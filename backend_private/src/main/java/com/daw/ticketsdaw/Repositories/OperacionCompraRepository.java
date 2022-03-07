package com.daw.ticketsdaw.Repositories;

import com.daw.ticketsdaw.Entities.OperacionCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperacionCompraRepository extends JpaRepository<OperacionCompra, Integer> {

    @Query(value = "select * from operacion_compra where month(fecha_compra)=?1 and esta_finalizada=1",nativeQuery = true)
    public List<OperacionCompra> getAllOfMonth(int month);
}
