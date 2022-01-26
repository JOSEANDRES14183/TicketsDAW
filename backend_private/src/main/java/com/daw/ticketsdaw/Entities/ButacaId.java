package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class ButacaId implements Serializable {

    private Sala sala;

    private int posX;

    private int posY;
}
