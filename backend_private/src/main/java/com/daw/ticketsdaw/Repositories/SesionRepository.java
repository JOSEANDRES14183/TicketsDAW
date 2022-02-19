package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Evento;
import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Entities.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Integer> {
    long countByFechaIniBetween(Date startDate, Date endDate);

    List<Sesion> findAllBySala(Sala sala);

    long countByEventoAndEstaOcultoIsFalse(Evento evento);
}
