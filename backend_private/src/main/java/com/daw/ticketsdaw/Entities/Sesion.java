package com.daw.ticketsdaw.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "sesion")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Sesion {

    @Transient
    private final String dateTimeFormat = "yyyy-MM-dd'T'HH:mm";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "entradas_max")
    @Min(1)
    private int entradasMax;

    @NotNull
    @Column(name = "fecha_fin_venta")
    @DateTimeFormat(pattern = dateTimeFormat)
    private Date fechaFinVenta;

    @NotNull
    @Column(name = "fecha_ini")
    @DateTimeFormat(pattern = dateTimeFormat)
    private Date fechaIni;

    @NotNull
    @Min(1)
    private int duracion;

    @NotNull
    @Column(name = "esta_oculto")
    private boolean estaOculto = true;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_evento", referencedColumnName = "id")
    @NotNull
    @JsonIgnore
    private Evento evento;

    @ToString.Exclude
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_sala", referencedColumnName = "id")
    @NotNull
    private Sala sala;

    public String getFechaFinFormatted() {
        return DateFormatUtils.format(DateUtils.addMinutes(fechaIni, duracion), dateTimeFormat);
    }
}
