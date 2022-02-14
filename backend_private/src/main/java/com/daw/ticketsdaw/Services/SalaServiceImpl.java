package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Butaca;
import com.daw.ticketsdaw.Entities.ButacaId;
import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Entities.TipoButaca;
import com.daw.ticketsdaw.Repositories.SalaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class SalaServiceImpl implements SalaService{

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private ButacaService butacaService;

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
            if(butaca.getButacaId().getPosY()>rows){
                rows = butaca.getButacaId().getPosY();
            }
            if(butaca.getButacaId().getPosX()>columns){
                columns = butaca.getButacaId().getPosX();
            }
        }

        List<ArrayList<Butaca>> layout = new ArrayList<>();

        while(layout.size()<rows+1){
            layout.add(new ArrayList<>(Arrays.asList(new Butaca[columns+1])));
        }

        for (Butaca butaca: butacas){
            layout.get(butaca.getButacaId().getPosY()).set(butaca.getButacaId().getPosX(),butaca);
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

    @Override
    public void setButacasJson(Sala sala, String json) throws JsonProcessingException {
        JsonNode node = new ObjectMapper().readTree(json);
        Iterator<JsonNode> iterator = node.iterator();
        int counter = 1;

        while (iterator.hasNext()){
            Iterator<JsonNode> row = iterator.next().iterator();
            while (row.hasNext()){
                JsonNode cell = row.next();
                if (!cell.isNull()){
                    Butaca butaca = new Butaca();

                    ButacaId butacaId = new ButacaId();
                    butacaId.setSala(sala.getId());
                    butacaId.setPosX(cell.get("butacaId").get("posX").intValue());
                    butacaId.setPosY(cell.get("butacaId").get("posY").intValue());

                    butaca.setButacaId(butacaId);
                    butaca.setSala(sala);
                    butaca.setNumButaca(counter);
                    butaca.setTipoButaca(new TipoButaca(1));
                    butacaService.save(butaca);
                    counter++;
                }
            }
        }

    }

}
