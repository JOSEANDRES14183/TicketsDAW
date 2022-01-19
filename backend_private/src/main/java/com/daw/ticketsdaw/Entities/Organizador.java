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

    @Column(name = "enlace_twitter")
    @Size(max = 64)
    private String enlaceTwitter;

    @Column(name = "enlace_instagram")
    @Size(max = 64)
    private String enlaceInstagram;

    @Column(name = "enlace_facebook")
    @Size(max = 64)
    private String enlaceFacebook;

    @Column(name = "enlace_personal")
    @Size(max = 64)
    private String enlacePersonal;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "foto_perfil")
    private RecursoMedia fotoPerfil;

    @OneToMany(mappedBy = "organizador") //Hace referencia al nombre de la propiedad (Java)
    //@JoinColumn(name = "id_organizador") //Hace referencia al nombre de la columna (MySQL)
    private List<Evento> eventos;

}
