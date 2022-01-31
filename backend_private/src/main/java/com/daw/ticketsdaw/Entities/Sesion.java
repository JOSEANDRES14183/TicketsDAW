package com.daw.ticketsdaw.Entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "sesion")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "entradas_max")
    private int entradasMax;

    @NotNull
    @Column(name = "fecha_fin_venta")
    private LocalDateTime fechaFinVenta;

    @NotNull
    @Column(name = "fecha_ini")
    private LocalDateTime fechaIni;

    @NotNull
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @NotNull
    @Column(name = "esta_oculto")
    private boolean estaOculto = false;

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
