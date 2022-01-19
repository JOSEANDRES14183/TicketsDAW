package com.daw.ticketsdaw.Entities;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
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
    @ManyToOne
    @JoinColumn(name = "id_organizador", nullable = false)
    private Organizador idOrganizador;

    @ManyToOne
    @JoinColumn(name = "foto_perfil", nullable = false)
    private RecursoMedia fotoPerfil;

    @ManyToOne
    @JoinColumn(name = "documento_normas")
    private NormasEvento documentoNormas;
}
