package com.alra.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ServiceApplication {
    private static Logger logger = LoggerFactory.getLogger(ServiceApplication.class);

    public static void main(String[] args) {
        logger.info("Iniciando a api");
        SpringApplication.run(ServiceApplication.class, args);
        logger.info("Api iniciada com sucesso");
    }

}