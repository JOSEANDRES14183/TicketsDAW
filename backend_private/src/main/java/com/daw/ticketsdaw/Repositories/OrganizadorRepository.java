package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Integer> {
}
