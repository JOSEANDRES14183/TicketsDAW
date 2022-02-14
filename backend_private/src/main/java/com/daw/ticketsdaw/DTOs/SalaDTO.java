package com.daw.ticketsdaw.DTOs;

import com.daw.ticketsdaw.Entities.Ciudad;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class SalaDTO {
    private Integer id;

    @NotNull
    @Size(min = 3, max = 20)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 20)
    private String direccion;

    @Positive
    @Column(name = "aforo_max")
    private int aforoMax;

    @NotNull
    private double latitud;

    @NotNull
    private double longitud;

    @NotNull
    private boolean estaOculto;

    @NotNull
    private Ciudad ciudad;

}
