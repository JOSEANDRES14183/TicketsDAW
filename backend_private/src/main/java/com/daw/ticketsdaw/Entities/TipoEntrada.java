package com.daw.ticketsdaw.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "tipo_entrada")
public class TipoEntrada {
    @ToString.Exclude
    @EmbeddedId
    private TipoEntradaId primaryKey;

    @ToString.Exclude
    @MapsId("sesion")
    @ManyToOne
    @JoinColumn(name = "id_sesion", referencedColumnName = "id")
    @JsonIgnore
    private SesionNoNumerada entitySesion;

    @NotNull
    @Column(name = "max_entradas")
    @Min(1)
    private int maxEntradas;

    @NotNull
    @Min(0)
    private float precio;

    @OneToMany(mappedBy = "tipoEntrada")
    @JsonIgnore
    private List<Entrada> entradas;

}
