package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Sesion;
import com.daw.ticketsdaw.Repositories.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SesionService {

    @Autowired
    private SesionRepository sesionRepository;

    public void save(Sesion sesion){
        sesionRepository.save(sesion);
    }

    public void delete(Sesion sesion){
        sesionRepository.delete(sesion);
    }
}
