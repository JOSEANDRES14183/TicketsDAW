package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {}
