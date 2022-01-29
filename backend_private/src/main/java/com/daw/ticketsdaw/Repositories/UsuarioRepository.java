package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    @Query("SELECT usuario FROM Usuario usuario where usuario.nombreUsuario = ?1")
    Usuario findUsuarioByNombreUsuario(String nombreUsuario);
}
