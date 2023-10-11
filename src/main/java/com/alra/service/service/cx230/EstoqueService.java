package com.alra.service.service.cx230;

import com.alra.service.repository.cx230.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {
    @Autowired
    EstoqueRepository estoqueRepository;
}
