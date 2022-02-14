package com.daw.ticketsdaw.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class ButacaId implements Serializable {

    @JsonIgnore
    private Integer sala;

    @Column(name="pos_x")
    private int posX;

    @Column(name = "pos_y")
    private int posY;
}
