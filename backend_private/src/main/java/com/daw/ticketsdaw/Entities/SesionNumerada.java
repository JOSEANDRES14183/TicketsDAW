package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "sesion_no_numerada")
public class SesionNumerada extends Sesion{

    @NotNull
    @Column(name = "plazas_max")
    private int plazasMax;

}
