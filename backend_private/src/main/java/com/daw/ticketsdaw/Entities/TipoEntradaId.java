package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Data
@Embeddable
public class TipoEntradaId implements Serializable {
    private SesionNoNumerada sesion;

    private String nombre;
}
