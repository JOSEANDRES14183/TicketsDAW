package com.daw.ticketsdaw.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "butaca")
@IdClass(ButacaId.class)
public class Butaca {

    @Id
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_sala", referencedColumnName = "id")
    @JsonBackReference
    private Sala sala;

    @Id
    @Column(name = "pos_x")
    @JsonIgnore
    private int posX;

    @Id
    @Column(name = "pos_y")
    @JsonIgnore
    private int posY;

    @Column(name = "num_butaca")
    private int numButaca;

    @ManyToOne
    @JoinColumn(name = "tipo_butaca", referencedColumnName = "id")
    private TipoButaca tipoButaca;
}
