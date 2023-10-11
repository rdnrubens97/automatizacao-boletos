package com.alra.service.service.cx230;

import com.alra.service.repository.cx230.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;
}
