package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Repositories.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SalaService{

    public void create(Sala sala);

    /**
     *Reads all the rooms that are visible
     * @return returns a list of Sala with all non-hidden entries
     */
    public List<Sala> readVisible();

    /**
     *Reads a single room that is visible
     * @return returns a Sala with all non-hidden entries
     */
    public Sala readVisible(int id);

    /**
     *Reads all the rooms without filtering any entries
     * @return returns a list of all Sala
     */
    public List<Sala> read();

    /**
     *Reads a single room without filtering any entries
     * @return returns a Sala
     */
    public Sala read(int id);

    public void delete(Sala sala);

    public String getButacasJson(Sala sala);

    /**
     *Reads all the rooms that are visible and have seats set up
     * @return returns a list of Sala with all non-hidden entries with seats
     */
    public List<Sala> getSalasWithButacas();

}
