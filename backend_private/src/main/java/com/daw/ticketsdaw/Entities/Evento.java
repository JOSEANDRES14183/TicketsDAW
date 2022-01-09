package com.daw.ticketsdaw.Entities;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String titulo;
    private String descripcion;
    @Column(name = "edad_minima", nullable = false)
    private byte edadMinima;
    @Column(name = "esta_oculto", nullable = false)
    private boolean estaOculto;
    @Column(name = "es_nominativo", nullable = false)
    private boolean esNominativo;
    @Column(name = "duracion_estandar", nullable = false)
    private int duracionEstandar;

    //Foreign keys
    @Column(name = "id_organizador", nullable = false)
    private int idOrganizador;
    @Column(name = "foto_perfil", nullable = false)
    private int fotoPerfil;
    @Column(name = "documento_normas")
    private int documentoNormas;
}
