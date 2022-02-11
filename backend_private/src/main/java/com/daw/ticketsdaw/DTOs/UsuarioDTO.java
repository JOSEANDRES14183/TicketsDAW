package com.daw.ticketsdaw.DTOs;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UsuarioDTO {

    @NotNull
    private int id;

    private boolean estaValidado = true;

    @Size(max = 30)
    private String nombre;

    @NotNull
    @Size(max = 15)
    private String nombreUsuario;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String passwordHash;

    @Size(max = 300)
    private String descripcion;

    MultipartFile fotoPerfil;

}
