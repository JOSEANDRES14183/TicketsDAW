package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Repositories.SalaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SalaService{

    public void create(Sala sala);

    public List<Sala> read();

    public Sala read(int id);

    public void delete(Sala sala);

    public boolean checkById(int id);

    public String getButacasJson(Sala sala);

    public List<Sala> getSalasWithButacas();

    public void setButacasJson(Sala sala,String json) throws JsonProcessingException;

}
