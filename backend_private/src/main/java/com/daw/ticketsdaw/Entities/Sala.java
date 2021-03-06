package com.daw.ticketsdaw.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;

@Data
@Entity
@Table(name = "sala")
public class Sala implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3, max = 20)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 20)
    private String direccion;

    @Positive
    @Column(name = "aforo_max")
    private int aforoMax;

    @NotNull
    private double latitud;

    @NotNull
    private double longitud;

    @NotNull
    @Column(name = "esta_oculto")
    private boolean estaOculto = true;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_propietario", referencedColumnName = "id")
    @NotNull
    @JsonIgnore
    private PropietarioSala propietarioSala;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="ciudad", referencedColumnName = "id")
    @NotNull
    @JsonManagedReference
    private Ciudad ciudad;

    @OneToMany(mappedBy = "sala")
    @JsonManagedReference
    private List<Butaca> butacas = new ArrayList<>();

    @OneToMany(mappedBy = "sala")
    @JsonBackReference
    private List<Sesion> sesiones = new ArrayList<>();

    public boolean hasButacas(){
        if(butacas == null || butacas.isEmpty() || butacas.size() < 1)
            return false;
        return true;
    }
}
