package com.daw.ticketsdaw.Entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "tipo_entrada")
@IdClass(TipoEntradaId.class)
public class TipoEntrada {
    @Id
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_sesion", referencedColumnName = "id")
    private SesionNoNumerada sesion;

    @Id
    private String nombre;

    @NotNull
    @Column(name = "max_entradas")
    private int maxEntradas;

    @NotNull
    private float precio;
}
