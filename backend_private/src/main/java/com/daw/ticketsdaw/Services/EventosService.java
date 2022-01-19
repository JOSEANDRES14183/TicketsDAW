package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Evento;
import com.daw.ticketsdaw.Repositories.EventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EventosService {
    @Autowired
    EventosRepository eventosRepository;

    public List<Evento> read(){
        return eventosRepository.findAll();
    }

    public Evento read(int id){
        return eventosRepository.findById(id).get();
    }

    public void save(Evento evento){
        eventosRepository.save(evento);
    }

    public void remove(Evento evento){
        eventosRepository.delete(evento);
    }
}
