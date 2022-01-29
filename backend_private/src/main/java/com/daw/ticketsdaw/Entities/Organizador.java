package com.daw.ticketsdaw.Entities;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
//@Table(name = "organizador")
public class Organizador extends Usuario{
    @NotNull
    @Size(max = 30)
    private String nombre;

    @Size(max = 300)
    private String descripcion;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "foto_perfil")
    private RecursoMedia fotoPerfil;

    @OneToMany(mappedBy = "organizador") //Hace referencia al nombre de la propiedad (Java)
    //@JoinColumn(name = "id_organizador") //Hace referencia al nombre de la columna (MySQL)
    private List<Evento> eventos;

}
