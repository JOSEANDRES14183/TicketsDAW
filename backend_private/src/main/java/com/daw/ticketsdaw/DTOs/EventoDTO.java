package com.daw.ticketsdaw.DTOs;

import com.daw.ticketsdaw.Entities.Categoria;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EventoDTO {
    private Integer id;
    @NotNull
    @Size(min = 3, max = 20)
    private String titulo;
    @NotNull
    @Size(max = 300)
    private String descripcion;
    @NotNull
    @Min(0)
    private byte edadMinima;
    @NotNull
    private boolean estaOculto;
    @NotNull
    private boolean esNominativo;
    @NotNull
    @Min(1)
    private int duracionEstandar;

    @NotNull
    private Categoria categoria;

    MultipartFile fotoPerfil;

    MultipartFile documentoNormas;
}
