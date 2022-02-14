package com.daw.ticketsdaw.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "butaca")
public class Butaca {

    @ToString.Exclude
    @EmbeddedId
    private ButacaId butacaId;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_sala", referencedColumnName = "id")
    @JsonBackReference
    @MapsId("sala")
    private Sala sala;

    @Column(name = "num_butaca")
    @JsonIgnore
    private int numButaca;

    @ManyToOne
    @JoinColumn(name = "tipo_butaca", referencedColumnName = "id")
    @JsonIgnore
    private TipoButaca tipoButaca;
}
