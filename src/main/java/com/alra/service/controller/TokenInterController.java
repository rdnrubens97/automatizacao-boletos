package com.alra.service.controller;

import com.alra.service.service.cxempresa.TokenInterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class TokenInterController {
    @Autowired
    TokenInterService tokenInterService;

    @PostMapping(value = "/token")
    public void atualizarToken() {
        tokenInterService.atualizarToken();
    }


}
