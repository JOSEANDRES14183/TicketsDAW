package com.daw.ticketsdaw.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "entrada")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_asistente")
    private String nombreAsistente;

    @Column(name = "apellidos_asistente")
    private String apellidosAsistente;

    @Column(name = "correo_asistente")
    private String emailAsistente;



    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "nombre_tipo_entrada", referencedColumnName = "nombre"),
            @JoinColumn(name = "id_sesion_no_numerada", referencedColumnName = "id_sesion")
    })
    @ToString.Exclude
    @JsonIgnore
    private TipoEntrada tipoEntrada;

    @ManyToOne
    @JoinColumn(name = "id_sesion_numerada", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private SesionNumerada sesionNumerada;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "id_operacion_compra", referencedColumnName = "id")
    private OperacionCompra operacionCompra;





}
