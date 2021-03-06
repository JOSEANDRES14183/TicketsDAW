package com.daw.ticketsdaw.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

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
