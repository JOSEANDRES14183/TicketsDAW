package com.daw.ticketsdaw.DTOs;
import com.daw.ticketsdaw.Entities.Sala;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SesionNumeradaDTO {

    private Integer id;

    @NotNull
    private int entradasMax;

    @NotNull
    private Date fechaFinVenta;

    @NotNull
    private Date fechaIni;

    @NotNull
    private Date fechaFin;

    @NotNull
    private Sala sala;

}
