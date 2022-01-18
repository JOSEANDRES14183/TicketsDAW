package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Ciudad;
import com.daw.ticketsdaw.Repositories.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadServiceImpl implements CiudadService{

    @Autowired
    private CiudadRepository ciudadRepository;

    @Override
    public List<Ciudad> read() {
        return ciudadRepository.findAll();
    }

    @Override
    public Ciudad read(int id) {
        return ciudadRepository.findById(id).get();
    }
}
