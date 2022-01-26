package com.daw.ticketsdaw.Entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(min = 3, max = 20)
    private String titulo;
    @Size(max = 300)
    private String descripcion;
    @Column(name = "edad_minima", nullable = false)
    private byte edadMinima;
    @Column(name = "esta_oculto", nullable = false)
    private boolean estaOculto;
    @Column(name = "es_nominativo", nullable = false)
    private boolean esNominativo;
    @Column(name = "duracion_estandar", nullable = false)
    private int duracionEstandar;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "id_organizador", nullable = false)
    private Organizador organizador;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "foto_perfil", nullable = false)
    private RecursoMedia fotoPerfil;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "documento_normas")
    private NormasEvento documentoNormas;

    @OneToMany(mappedBy = "eventoGaleriaImagenes")
    List<RecursoMedia> galeriaImagenes;

    @OneToMany(mappedBy = "eventoGaleriaVideos")
    List<RecursoMedia> galeriaVideos;
}
