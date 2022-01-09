package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Repositories.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SalaService{

    public void create();

    public List<Sala> read();

    public Optional<Sala> read(int id);

    public void update();

    public void delete();

}
