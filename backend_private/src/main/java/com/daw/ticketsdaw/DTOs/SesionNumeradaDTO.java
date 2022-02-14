package com.daw.ticketsdaw.DTOs;
import com.daw.ticketsdaw.Entities.Sala;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SesionNumeradaDTO extends SesionDTO{
    @NotNull
    private float precio;
}
