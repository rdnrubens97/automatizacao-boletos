package com.alra.service.service.cx230;

import com.alra.service.repository.cx230.KeySistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeySistemaService {
    @Autowired
    KeySistemaRepository keySistemaRepository;
}
