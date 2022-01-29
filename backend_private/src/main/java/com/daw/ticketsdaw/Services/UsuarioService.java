package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Usuario;
import com.daw.ticketsdaw.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void create(Usuario usuario){
        usuarioRepository.save(usuario);
    }

}
