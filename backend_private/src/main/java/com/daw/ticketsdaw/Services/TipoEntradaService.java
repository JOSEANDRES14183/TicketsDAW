package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.TipoEntrada;
import com.daw.ticketsdaw.Repositories.TipoEntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TipoEntradaService {
    @Autowired
    TipoEntradaRepository tipoEntradaRepository;

    public void save(TipoEntrada tipoEntrada){
        tipoEntradaRepository.save(tipoEntrada);
    }
}
