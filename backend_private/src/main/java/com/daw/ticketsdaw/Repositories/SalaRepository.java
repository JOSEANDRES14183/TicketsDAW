package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala,Integer>{

    @Query("SELECT DISTINCT sala FROM Sala sala INNER JOIN Butaca butaca on butaca.sala = sala WHERE sala.estaOculto = false")
    List<Sala> getSalasWithButacas();

    Optional<Sala> findByIdAndEstaOcultoIsFalse(int id);

    List<Sala> findByEstaOcultoIsFalse();
}
