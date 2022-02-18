package com.daw.ticketsdaw.DTOs;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CopySesionDTO {
    @Transient
    private final String dateTimeFormat = "yyyy-MM-dd'T'HH:mm";

    @NotNull
    @Min(1)
    private Integer numDays;

    @NotNull
    @DateTimeFormat(pattern = dateTimeFormat)
    private Date endDate;
}
