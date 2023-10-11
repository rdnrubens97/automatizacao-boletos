package com.alra.service.service.cxempresa;

import com.alra.service.model.cxempresa.boletoregistrado.BoletoRegistrado;
import com.alra.service.model.cxempresa.boletoxml.BoletoXml;
import com.alra.service.repository.cx230.MarketingRepository;
import com.alra.service.repository.cxempresa.BoletoRegistradoRepository;
import com.alra.service.repository.cxempresa.BoletoXmlRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoletoXmlService {
    @Autowired
    LogService logService;
    @Autowired
    BoletoXmlRepository boletoXmlRepository;
    @Autowired
    MarketingRepository marketingRepository;
    @Autowired
    BoletoRegistradoRepository boletoRegistradoRepository;
    @Autowired
    private BoletoRegistradoService boletoRegistradoService;
    @Autowired
    EnvioEmailService envioEmailService;
    @Autowired
    FtpService ftpService;
    @Value("classpath:imagens/alra.jpg")
    private Resource alraLogo;
    @Value("classpath:imagens/inter.jpg")
    private Resource interLogo;
    @Value("classpath:ireport/BoletoWeb.jasper")
    private Resource boletoJasper;
    @Value("${pdf.output.directory}")
    private String pdfOutputDirectory;

    public void gerarXmlESalvarNaAuxBoletoXml() {
        List<BoletoRegistrado> boletosRegistrados = boletoRegistradoService.selecionarBoletosRegistradosParaGerarXml();
        try {
            JAXBContext context = JAXBContext.newInstance(BoletoRegistrado.class);
            Marshaller marshaller = context.createMarshaller();

            for (BoletoRegistrado boletoRegistrado : boletosRegistrados) {
                StringWriter sw = new StringWriter();
                marshaller.marshal(boletoRegistrado, sw);
                BoletoXml boletoXml = new BoletoXml();
                boletoXml.setSeuNumero(boletoRegistrado.getSeuNumero());
                boletoXml.setNome(boletoRegistrado.getNomePagador());
                boletoXml.setCpfCnpj(boletoRegistrado.getCpfCnpjPagador());
                boletoXml.setXml(sw.toString());
                boletoXmlRepository.save(boletoXml);
                boletoRegistrado.setGeradoXml(true);
                boletoRegistradoRepository.save(boletoRegistrado);
            }
        } catch (Exception e) {
            try {
                throw new Exception("Erro ao transformar e salvar boletos XML: " + e.getMessage());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Converte uma lista de documentos XML em arquivos PDF de boletos,
     * e posteriormente, envia esses arquivos PDF via FTP.
     */
    public void boletoXmlParaPdf() {
        List<BoletoXml> boletosXml = boletoXmlRepository.findByEnviadoClienteFalse();
        try {
            // Carrega as imagens de logos utilizadas nos boletos
            BufferedImage alraLogoImage = ImageIO.read(alraLogo.getInputStream());
            BufferedImage interLogoImage = ImageIO.read(interLogo.getInputStream());

            // Configura os parâmetros a serem passados para o relatório
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("logo", alraLogoImage);
            parameters.put("logointer", interLogoImage);

            // Itera sobre cada XML na lista
            for (BoletoXml boletoXml : boletosXml) {
                String xml = boletoXml.getXml();
                // extrai o valore relativo à tag nossoNumero do xml
                String nossoNumero = extrairTagXml(xml, "nossoNumero");
                String seuNumero = extrairTagXml(xml, "seuNumero");
                String cpfCnpjPagador = extrairTagXml(xml, "cpfCnpjPagador");
                String email = cpfCnpjPagador.length() == 11 ? marketingRepository.selecionarEmailPessoaFisica(cpfCnpjPagador) :
                        marketingRepository.selecionarEmailPessoaJuridica(cpfCnpjPagador);
                String emailDestino = email != null ? email : "";

                try (InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
                    // Cria um DataSource a partir do XML
                    JRXmlDataSource jrXmlDataSource = new JRXmlDataSource(stream);

                    // Preenche o relatório com os dados do DataSource
                    JasperPrint jp = JasperFillManager.fillReport(
                            this.getClass().getResourceAsStream("/ireport/BoletoWeb.jasper"),
                            parameters,
                            jrXmlDataSource
                    );
                    // Preparar o fluxo de saída diretamente para o servidor remoto via FTP
                    ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
                    JasperExportManager.exportReportToPdfStream(jp, pdfOutputStream);


                    envioEmailService.enviarBoletoPorEmail(emailDestino, pdfOutputStream.toByteArray(), nossoNumero + ".pdf", nossoNumero, seuNumero);

                    // Enviar o arquivo PDF diretamente para o servidor remoto via FTP
                        ftpService.enviarBoletoViaFtpStream(new ByteArrayInputStream(pdfOutputStream.toByteArray()), nossoNumero + ".pdf", extrairTagXml(xml, "nossoNumero"));
                        boletoXml.setEnviadoCliente(true);
                        boletoXmlRepository.save(boletoXml);
                        logService.gerarLog(boletoXml.getSeuNumero(),
                               "Boleto código: " + nossoNumero + " enviado para o servidor",
                                "ENVIO REALIZADO");
                } catch (Exception e) {
                    String errorMessage = "Erro ao enviar boleto código: " + nossoNumero + " para o servidor. " + e.getMessage();
                    logService.gerarLog(seuNumero, errorMessage, "ENVIO ERRO");
                }
            }
        } catch (Exception e) {
            logService.gerarLog(" - ", e.getMessage(), "ENVIO ERRO");
        }
    }

    /**
     * Extrai o valor do elemento "nossoNumero" de um documento XML.
     *
     * @param xml O conteúdo do documento XML a ser analisado.
     * @return O valor do elemento "nossoNumero" ou null se não encontrado ou em caso de erro.
     */
    public static String extrairTagXml(String xml, String tag) {
        try {
            // Configuração para criar um documento a partir do XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xml.getBytes()));

            // Obtém uma lista de elementos com a tag "nossoNumero"
            NodeList nodeList = document.getElementsByTagName(tag);

            // Verifica se a lista contém elementos
            if (nodeList.getLength() > 0) {
                // Pega o primeiro elemento da lista (supondo que haja apenas um)
                Node node = nodeList.item(0);

                // Verifica se o nó é do tipo Element (uma tag)
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    // Retorna o conteúdo do elemento "nossoNumero"
                    return element.getTextContent();
                }
            }
        } catch (Exception e) {
            // Em caso de erro, imprime a exceção
            e.printStackTrace();
        }
        // Retorna null se não encontrado ou em caso de erro
        return null;
    }


}
