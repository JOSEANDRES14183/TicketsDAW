package com.daw.ticketsdaw.Converters;

import com.daw.ticketsdaw.Entities.Organizador;
import org.springframework.core.convert.converter.Converter;


public class StringToOrganitzadorConverter implements Converter<String, Organizador> {
    @Override
    public Organizador convert(String from){
        Organizador organizador = new Organizador();
        organizador.setId(Integer.parseInt(from));
        return organizador;
    }
}
