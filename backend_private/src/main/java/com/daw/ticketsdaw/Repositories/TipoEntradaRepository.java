package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.TipoEntrada;
import com.daw.ticketsdaw.Entities.TipoEntradaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoEntradaRepository extends JpaRepository<TipoEntrada, TipoEntradaId> {
}
