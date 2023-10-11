package com.alra.service.model.cxempresa.token;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tbl_token")
@Entity
public class TokenInter {
    @Id()
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "expires_in")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Sao_Paulo")
    private Date expiresIn;
    @Column(name = "scope")
    private String scope;
    @Column(name = "token_type")
    private String tokenType;

    public TokenInter(TokenInterDto tokenInterDto) {
        this.accessToken = tokenInterDto.getAccessToken();
        this.tokenType = tokenInterDto.getTokenType();
        this.expiresIn = Timestamp.valueOf(LocalDateTime.now().plusSeconds(tokenInterDto.getExpiresIn()));
        this.scope = tokenInterDto.getScope();
    }

    public boolean atualizado() {
        return LocalDateTime.now().isBefore(this.expiresIn.toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime().minus(10, ChronoUnit.MINUTES));
    }

    @Override
    public String toString() {
        return "TokenInter" +
                "\naccessToken: " + accessToken +
                "\ntokenType: " + tokenType +
                "\nexpiresIn: " + expiresIn +
                "\nscope:" + scope;
    }

}
