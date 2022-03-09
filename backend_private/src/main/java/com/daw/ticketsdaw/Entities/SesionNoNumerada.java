package com.daw.ticketsdaw.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "sesion_no_numerada")
public class SesionNoNumerada extends Sesion{
    @OneToMany(mappedBy = "entitySesion", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    List<TipoEntrada> tiposEntrada;
}
