package com.alra.service.service.cxempresa;

import com.alra.service.model.cxempresa.token.TokenInter;
import com.alra.service.model.cxempresa.token.TokenInterDto;
import com.alra.service.repository.cxempresa.TokenInterDtoRepository;
import com.alra.service.repository.cxempresa.TokenInterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenInterService {
    @Autowired
    TokenInterRepository tokenInterRepository;
    @Autowired
    TokenInterDtoRepository tokenInterDtoRepository;
    @Autowired
    RestTemplate restTemplate;
    @Value("${inter.api.client-id}")
    private String clientId;
    @Value("${inter.api.client-secret}")
    private String clientSecret;

    @Transactional
    @Modifying
    public void atualizarToken() {
        Optional<TokenInter> tokenInterOptional = tokenInterRepository.findAll().stream().findFirst();
        if (tokenInterOptional.isEmpty() || !tokenInterOptional.get().atualizado()) {
            TokenInter tokenInter = gerarToken();
            tokenInterRepository.deleteAll();
            tokenInterRepository.save(tokenInter);
        }
    }

    public TokenInter gerarToken() {
        try {
            String url = "https://cdpj.partners.bancointer.com.br/oauth/v2/token";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);
            map.add("grant_type", "client_credentials");
            map.add("scope", "boleto-cobranca.read boleto-cobranca.write");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            ResponseEntity<TokenInterDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    TokenInterDto.class
            );

            TokenInterDto tokenDto = response.getBody();
            TokenInter token = new TokenInter(tokenDto);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter o token de autenticação do Banco Inter", e);
        }
    }

    public String buscarAccessToken() {
        Optional<TokenInter> tokenInterOptional = Optional.ofNullable(tokenInterRepository.findAll().get(0));
        if (tokenInterOptional.isPresent()) {
            String accessToken = tokenInterOptional.get().getAccessToken();
            return accessToken;
        }
        else {
            return "Nenhum token encontrado";
        }
    }


}
