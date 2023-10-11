package com.alra.service.controller;

import com.alra.service.model.cx230.LiberadorDeSistema;
import com.alra.service.service.cx230.LiberadorDeSistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/liberador-sistema")
public class LiberadorDeSistemaController {
    @Autowired
    LiberadorDeSistemaService liberadorDeSistemaService;

    @GetMapping("/buscar-por-id")
    public ResponseEntity<LiberadorDeSistema> buscarLiberadorPorId (){
        LiberadorDeSistema liberadorDeSistema = liberadorDeSistemaService.buscarLiberadorDeSistemaPorIdSistema("22022021545011");
        return ResponseEntity.ok().body(liberadorDeSistema);
    }

}
