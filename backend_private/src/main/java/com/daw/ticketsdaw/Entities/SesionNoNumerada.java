package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "sesion_no_numerada")
public class SesionNoNumerada extends Sesion{
    @OneToMany(mappedBy = "sesion")
    List<TipoEntrada> tiposEntrada;
}
