package com.daw.ticketsdaw.DTOs;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Data
public class GaleriaDTO {
    @NotNull
    private MultipartFile media;
    @NotNull
    private int prioridad;
}
