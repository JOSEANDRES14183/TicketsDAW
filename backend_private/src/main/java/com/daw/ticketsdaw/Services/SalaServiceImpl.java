package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Repositories.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
