package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "butaca")
public class Butaca {

    @Id
    private int id;

    @Column(name = "pos_x")
    private int posX;

    @Column(name = "pos_y")
    private int posY;

    @Column(name = "num_butaca")
    private int numButaca;

    @Column(name = "tipo_butaca")
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private TipoButaca tipoButaca;
}
