package com.daw.ticketsdaw.DTOs;

import com.daw.ticketsdaw.Entities.Sesion;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CalendarEventDTO {
    String title;
    String start;
    String end;
    String backgroundColor;
    String borderColor;

    Map<String, String> extendedProps = new HashMap<>();

    public CalendarEventDTO(Sesion sesion){
        setTitle(sesion.getDuracion() + " min.");
        setStart(sesion.getFechaIni().toString());
        setEnd(sesion.getFechaFinFormatted());
        getExtendedProps().put("sesion_id", sesion.getId().toString());
        if(sesion.isEstaOculto()){
            setBackgroundColor("red");
            setBorderColor("#E50000");
        }
    }
}
