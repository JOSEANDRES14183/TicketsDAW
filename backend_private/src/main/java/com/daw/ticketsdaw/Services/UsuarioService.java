package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Usuario;
import com.daw.ticketsdaw.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void create(Usuario usuario){
        usuarioRepository.save(usuario);
    }


    public Usuario getByNombreUsuario(String username){
        return usuarioRepository.findUsuarioByNombreUsuario(username);
    }

    public Usuario getById(int id){
        return usuarioRepository.findById(id).get();
    }

    public void delete(Usuario usuario){
        usuarioRepository.delete(usuario);
    }

}
