package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Integer, Categoria> {
}
