package com.daw.ticketsdaw.Entities;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class RecursoMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre_archivo", nullable = false, unique = true)
    private String nombreArchivo;
    @NotNull
    private int prioridad;
}
