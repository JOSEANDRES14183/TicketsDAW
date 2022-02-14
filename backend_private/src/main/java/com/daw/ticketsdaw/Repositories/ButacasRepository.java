package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Butaca;
import com.daw.ticketsdaw.Entities.ButacaId;
import com.daw.ticketsdaw.Entities.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ButacasRepository extends JpaRepository<Butaca, ButacaId> {

    public void deleteAllBySala(Sala sala);
}
