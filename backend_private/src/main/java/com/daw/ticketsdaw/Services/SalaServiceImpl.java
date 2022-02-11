package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Butaca;
import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Repositories.SalaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SalaServiceImpl implements SalaService{

    @Autowired
    private SalaRepository salaRepository;

    @Override
    public void create(Sala sala){ salaRepository.save(sala); }

    @Override
    public List<Sala> readVisible() {
        return salaRepository.findByEstaOcultoIsFalse();
    }

    @Override
    public Sala readVisible(int id) {
        return salaRepository.findByIdAndEstaOcultoIsFalse(id).get();
    }

    @Override
    public List<Sala> read() {
        return salaRepository.findAll();
    }

    @Override
    public Sala read(int id) {
        return salaRepository.findById(id).get();
    }

    @Override
    public void delete(Sala sala){
        salaRepository.delete(sala);
    }

    @Override
    public String getButacasJson(Sala sala) {
        List<Butaca> butacas = sala.getButacas();
        int rows=0;
        int columns=0;

        for (Butaca butaca : butacas){
            if(butaca.getPosY()>rows){
                rows = butaca.getPosY();
            }
            if(butaca.getPosX()>columns){
                columns = butaca.getPosX();
            }
        }

        List<ArrayList<Butaca>> layout = new ArrayList<>();

        while(layout.size()<rows+1){
            layout.add(new ArrayList<>(columns+1));
        }

        for (Butaca butaca: butacas){
            layout.get(butaca.getPosY()).add(butaca.getPosX(),butaca);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            return mapper.writeValueAsString(layout);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Sala> getSalasWithButacas() {
        return salaRepository.getSalasWithButacas();
    }
}
