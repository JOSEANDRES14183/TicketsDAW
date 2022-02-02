package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Data
@Embeddable
public class TipoEntradaId implements Serializable {
    public TipoEntradaId(Integer sesion, String nombre) {
        this.sesion = sesion;
        this.nombre = nombre;
    }

    public TipoEntradaId() {
    }

    private Integer sesion;

    private String nombre;
}
