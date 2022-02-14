package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "sesion_numerada")
public class SesionNumerada extends Sesion{

    @NotNull
    @Min(0)
    private float precio;

}
