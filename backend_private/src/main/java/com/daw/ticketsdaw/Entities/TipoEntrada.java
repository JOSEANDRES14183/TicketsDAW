package com.daw.ticketsdaw.Entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "tipo_entrada")
public class TipoEntrada {
    @ToString.Exclude
    @EmbeddedId
    private TipoEntradaId primaryKey;

    @ToString.Exclude
    @MapsId("sesion")
    @ManyToOne
    @JoinColumn(name = "id_sesion", referencedColumnName = "id")
    private SesionNoNumerada entitySesion;

    @NotNull
    @Column(name = "max_entradas")
    private int maxEntradas;

    @NotNull
    private float precio;

}
