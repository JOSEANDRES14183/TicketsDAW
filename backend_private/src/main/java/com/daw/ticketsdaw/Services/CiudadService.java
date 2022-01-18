package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Ciudad;

import java.util.List;

public interface CiudadService {

    public List<Ciudad> read();

    public Ciudad read(int id);
}
