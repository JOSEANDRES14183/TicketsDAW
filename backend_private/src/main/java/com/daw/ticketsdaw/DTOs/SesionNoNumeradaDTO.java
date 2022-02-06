package com.daw.ticketsdaw.DTOs;

import com.daw.ticketsdaw.Entities.Evento;
import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Entities.TipoEntrada;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SesionNoNumeradaDTO extends SesionDTO{
    List<String> nombreTipo = new ArrayList<>();

    List<Integer> maxEntradasTipo = new ArrayList<>();

    List<Float> precioTipo = new ArrayList<>();
}
