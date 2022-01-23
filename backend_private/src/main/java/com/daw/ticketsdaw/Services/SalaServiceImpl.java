package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Butaca;
import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Repositories.SalaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalaServiceImpl implements SalaService{

    @Autowired
    private SalaRepository salaRepository;

    @Override
    public void create(Sala sala){ salaRepository.save(sala); }

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
    public boolean checkById(int id) {
        return salaRepository.existsById(id);
    }

    @Override
    public String getButacasJson(Sala sala) {

        List<Butaca> butacas = sala.getButacas();
        int butacasRows = 1;

        for (Butaca butaca : butacas){
            int currentRow = 0;
            if (butaca.getPosY()!=currentRow){
                currentRow = butaca.getNumButaca();
                butacasRows++;
            }
        }

        List<ArrayList<Butaca>> butacasAdaptadas = new ArrayList<>();
        while(butacasAdaptadas.size()<butacasRows){
            butacasAdaptadas.add(new ArrayList<>());
        }




        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            return mapper.writeValueAsString(sala.getButacas());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
