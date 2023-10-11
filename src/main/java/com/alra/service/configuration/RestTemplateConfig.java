package com.alra.service.configuration;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Configuration
public class RestTemplateConfig {
    @Value("${server.ssl.key-store}")
    private String keyStoreFile;
    @Value("${server.ssl.keyStoreType}")
    private String keyStoreType;
    private final RestTemplateProperties properties;

    public RestTemplateConfig(RestTemplateProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException, NoSuchProviderException {
        KeyStore clientStore = KeyStore.getInstance(keyStoreType);
        java.io.File file = ResourceUtils.getFile(keyStoreFile);
        clientStore.load(new FileInputStream(file), properties.getPassword().toCharArray());

        SSLContext sslContext = SSLContext.getInstance(properties.getProtocol());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(clientStore, properties.getPassword().toCharArray());
        sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(socketFactory).build();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(properties.getConnectionTimeout());
        requestFactory.setConnectionRequestTimeout(properties.getReadTimeout());

        return new RestTemplate(requestFactory);
    }
}
