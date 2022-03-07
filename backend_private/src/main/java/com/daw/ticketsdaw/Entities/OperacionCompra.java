package com.daw.ticketsdaw.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "operacion_compra")
public class OperacionCompra {

    @Transient
    @JsonIgnore
    private final String dateTimeFormat = "yyyy-MM-dd'T'HH:mm";

    @Id
    private Integer id;

    @Column(name = "fecha_compra")
    @DateTimeFormat(pattern = dateTimeFormat)
    private LocalDate fechaCompra;

    @OneToMany(mappedBy = "operacionCompra")
    private List<Entrada> entradas;
}
