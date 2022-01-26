package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Categoria;
import com.daw.ticketsdaw.Repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService{
    @Autowired
    CategoriaRepository categoriaRepository;

    public List<Categoria> read(){
        return categoriaRepository.findAll();
    }

    public Categoria read(int id){
        return categoriaRepository.findById(id).get();
    }
}
