package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "esta_deshabilitado")
    private boolean estaDeshabilitado;

}
