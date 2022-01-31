package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Integer> {}
