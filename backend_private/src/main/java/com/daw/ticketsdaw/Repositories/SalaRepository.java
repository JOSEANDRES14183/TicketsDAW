package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaRepository extends JpaRepository<Sala,Integer>{

    @Query("SELECT DISTINCT sala FROM Sala sala INNER JOIN Butaca butaca on butaca.sala = sala")
    List<Sala> getSalasWithButacas();
}
