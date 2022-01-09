package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Evento;
import com.daw.ticketsdaw.Repositories.EventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventosService {
    @Autowired
    EventosRepository eventosRepository;

    public List<Evento> read(){
        return eventosRepository.findAll();
    }

    public Evento read(int id){
        return eventosRepository.findById(id).get();
    }
}
