package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Butaca;
import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Repositories.ButacasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ButacaService {

    @Autowired
    ButacasRepository butacasRepository;

    public void save(Butaca butaca){
        butacasRepository.save(butaca);
    }

    public void deleteBySala(Sala sala){
        butacasRepository.deleteAllBySala(sala);
    }
}
