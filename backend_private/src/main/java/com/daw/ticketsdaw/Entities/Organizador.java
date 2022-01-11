package com.daw.ticketsdaw.Entities;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
//@Table(name = "organizador")
public class Organizador extends Usuario{
    @NotNull
    private String nombre;
    @Column(name = "enlace_twitter")
    private String enlaceTwitter;
    @Column(name = "enlace_instagram")
    private String enlaceInstagram;
    @Column(name = "enlace_facebook")
    private String enlaceFacebook;
    @Column(name = "enlace_personal")
    private String enlacePersonal;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "foto_perfil")
    private RecursoMedia fotoPerfil;
}
