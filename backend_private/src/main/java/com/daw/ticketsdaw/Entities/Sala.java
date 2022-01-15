package com.daw.ticketsdaw.Entities;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.*;

@Data
@Entity
@Table(name = "sala")
public class Sala implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 3, max = 20)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 20)
    private String direccion;

    @Positive
    @Column(name = "aforo_max")
    private int aforoMax;

    @Column(name = "esta_oculto")
    private boolean estaOculto;

    @ManyToOne
    @JoinColumn(name = "id_propietario", referencedColumnName = "id")
    private PropietarioSala propietarioSala;

    @ManyToOne
    @JoinColumn(name="ciudad", referencedColumnName = "id")
    private Ciudad ciudad;

}
