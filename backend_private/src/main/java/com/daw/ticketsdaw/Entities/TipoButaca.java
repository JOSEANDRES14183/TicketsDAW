package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tipo_butaca")
public class TipoButaca {

    @Id
    private int id;

    private String nombre;

    @Column(name = "precio_extra")
    private float precioExtra;
}
