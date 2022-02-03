package com.daw.ticketsdaw.DTOs;
import com.daw.ticketsdaw.Entities.Sala;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SesionNumeradaDTO {

    @Transient
    private final String dateTimeFormat = "yyyy-MM-dd'T'HH:mm";

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
    private Sala sala;

}
