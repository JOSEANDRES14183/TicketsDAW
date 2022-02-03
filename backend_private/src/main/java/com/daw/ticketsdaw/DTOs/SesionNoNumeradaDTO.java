package com.daw.ticketsdaw.DTOs;

import com.daw.ticketsdaw.Entities.Evento;
import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Entities.TipoEntrada;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class SesionNoNumeradaDTO {

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
    @DateTimeFormat(pattern = dateTimeFormat)
    private Date fechaFin;

    @NotNull
    private boolean estaOculto;

    @ToString.Exclude
    @NotNull
    private Sala sala;

    List<String> nombreTipo;

    List<Integer> maxEntradasTipo;

    List<Float> precioTipo;
}
