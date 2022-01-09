package com.daw.ticketsdaw.Entities;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sala")
public class Sala implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String direccion;

    @Column(name = "ciudad")
    private int id_ciudad;

    private int aforo_max;
    private boolean esta_oculto;
    private int id_propietario;


}
