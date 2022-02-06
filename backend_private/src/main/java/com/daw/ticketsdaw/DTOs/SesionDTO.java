package com.daw.ticketsdaw.DTOs;

import com.daw.ticketsdaw.Entities.Sala;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SesionDTO {
    @Transient
    private final String dateTimeFormat = "yyyy-MM-dd'T'HH:mm";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private int entradasMax;

    @NotNull
    @DateTimeFormat(pattern = dateTimeFormat)
    private Date fechaFinVenta;

    @NotNull
    @DateTimeFormat(pattern = dateTimeFormat)
    private Date fechaIni;

    @NotNull
    private int duracion;

    @NotNull
    private boolean estaOculto;

    @ToString.Exclude
    @NotNull
    private Sala sala;
}
