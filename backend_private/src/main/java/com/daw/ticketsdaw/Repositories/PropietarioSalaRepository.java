package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.PropietarioSala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioSalaRepository extends JpaRepository<PropietarioSala, Integer> { }
