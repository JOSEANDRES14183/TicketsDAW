package com.daw.ticketsdaw.DTOs;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class PropietarioSalaDTO {

    @NotNull
    @Size(max = 15)
    private String nombreUsuario;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 5)
    private String passwordHash;

}
