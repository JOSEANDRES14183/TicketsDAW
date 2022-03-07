package com.daw.ticketsdaw.Entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "sesion_numerada")
public class SesionNumerada extends Sesion{

    @NotNull
    @Min(0)
    private float precio;

    @OneToMany(mappedBy = "sesionNumerada")
    private List<Entrada> entradas;

}
