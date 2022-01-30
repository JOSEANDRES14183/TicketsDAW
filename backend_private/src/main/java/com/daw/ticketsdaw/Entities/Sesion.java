package com.daw.ticketsdaw.Entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "sesion")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "entradas_max")
    private int entradasMax;

    @NotNull
    @Column(name = "fecha_fin_venta")
    private Date fechaFinVenta;

    @NotNull
    @Column(name = "fecha_ini")
    private Date fechaIni;

    @NotNull
    @Column(name = "fecha_fin")
    private Date fechaFin;

    @NotNull
    @Column(name = "esta_oculto")
    private boolean estaOculto;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_evento", referencedColumnName = "id")
    @NotNull
    private Evento evento;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_sala", referencedColumnName = "id")
    @NotNull
    private Sala sala;



}
