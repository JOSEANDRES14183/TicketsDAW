package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ciudad")
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

}
