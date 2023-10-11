package com.alra.service.service.cxempresa;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FtpService {
    @Value("${ftp.server}")
    private String ftpServer;
    @Value("${ftp.port}")
    private int ftpPort;
    @Value("${ftp.username}")
    private String ftpUsername;
    @Value("${ftp.password}")
    private String ftpPassword;
    @Value("${ftp.remoteDirectory}")
    private String ftpRemoteDirectory;
    @Autowired
    private ResourceLoader resourceLoader;

    public void enviarBoletoViaFtpStream(InputStream inputStream, String fileName, String codigoBoletoParaLog) {
        FTPClient ftp = new FTPClient();

        try {
            // Conexão FTP
            ftp.connect(ftpServer, ftpPort);
            ftp.login(ftpUsername, ftpPassword);
            ftp.enterLocalActiveMode();

            // Mudar para o diretório remoto
            if (!ftp.changeWorkingDirectory(ftpRemoteDirectory)) {
                throw new Exception("Diretório remoto não encontrado");
            }

            // Envio do arquivo a partir do fluxo de entrada
            if (!ftp.storeFile(fileName, inputStream)) {
                throw new Exception("Erro ao enviar o arquivo via FTP");
            }

            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public byte[] baixarBoletoDoFTP(String nomeBoleto) {
        FTPClient ftp = new FTPClient();
        InputStream inputStream = null;

        try {
            // Conexão FTP
            ftp.connect(ftpServer, ftpPort);
            ftp.login(ftpUsername, ftpPassword);
            ftp.enterLocalActiveMode();

            // Mudar para o diretório remoto
            if (!ftp.changeWorkingDirectory(ftpRemoteDirectory)) {
                throw new Exception("Diretório remoto não encontrado");
            }

            // Baixar o arquivo como um fluxo de entrada
            inputStream = ftp.retrieveFileStream(nomeBoleto);

            if (inputStream == null) {
                throw new Exception("Arquivo não encontrado no servidor FTP");
            }

            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (ftp.isConnected()) {
                    ftp.logout();
                    ftp.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
