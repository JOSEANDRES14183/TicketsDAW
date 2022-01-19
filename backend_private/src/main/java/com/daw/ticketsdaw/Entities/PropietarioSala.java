package com.daw.ticketsdaw.Entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "propietario_sala")
public class PropietarioSala extends Usuario {

    @OneToMany(mappedBy = "propietarioSala")
    private List<Sala> salas = new ArrayList<>();

}
