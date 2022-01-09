package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala,Integer>{}
