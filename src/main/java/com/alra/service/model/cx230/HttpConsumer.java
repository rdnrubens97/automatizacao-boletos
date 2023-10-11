package com.alra.service.model.cx230;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpConsumer {
    @Autowired
    RestTemplate restTemplate;

    public String requisicaoGet(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Erro na resposta HTTP: " + response.getStatusCode());
            }
        } catch (Exception e){
            throw new RuntimeException("Erro na chamada HTTP: " + e.getMessage(), e);
        }
    }

}
