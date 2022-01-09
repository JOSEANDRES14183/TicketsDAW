package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventosRepository extends JpaRepository <Evento, Integer> {
}
