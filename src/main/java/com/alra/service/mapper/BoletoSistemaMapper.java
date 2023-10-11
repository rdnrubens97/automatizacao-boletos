package com.alra.service.mapper;

import com.alra.service.model.cxempresa.boletoregistrado.BoletoRegistrado;
import com.alra.service.model.cxempresa.boletosistema.BoletoSistema;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class BoletoSistemaMapper {
    private final ModelMapper mapper;

    public BoletoSistemaMapper() {
        this.mapper = new ModelMapper();
        configureMappings();
    }

    private void configureMappings() {
        mapper.addMappings(new PropertyMap<BoletoRegistrado, BoletoSistema>() {
            @Override
            protected void configure() {
                map().setIdSistema(source.getSeuNumero());
                map().setDataEmissao(source.getEmitidoEm());
                map().setLink(null);
                map().setStatus(1);
                map().setValor(source.getValorNominal());
                map().setVencimento(source.getDataVencimento());
                map().setCodigo(source.getNossoNumero());
                map().setPagamentoStatus(0);
                map().setDataPagamento(null);
                map().setValorPago(null);
                map().setTaxa(null);
                map().setNome(source.getNomePagador());
                map().setCpf(source.getCpfCnpjPagador());
                map().setTelefone(null);
                map().setLiberacao(0);
                map().setQrCode(null);
                map().setEnvioWhatsApp(null);
                map().setEmail(null);
                map().setEmailStatus(0);
            }
        });
    }

    public BoletoSistema mapearBoletoRegistradoParaBoletoSistema(BoletoRegistrado boletoRegistrado) {
        BoletoSistema boletoSistema = mapper.map(boletoRegistrado, BoletoSistema.class);
        return boletoSistema;
    }

}
