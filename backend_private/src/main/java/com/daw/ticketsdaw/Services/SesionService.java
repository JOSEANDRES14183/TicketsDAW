package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Sesion;
import com.daw.ticketsdaw.Exceptions.InvalidSaveException;
import com.daw.ticketsdaw.Repositories.SesionRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SesionService {

    @Autowired
    private SesionRepository sesionRepository;

    /**
     * Saves a Sesion object to the database and returns whether it has saved without overlapping with another Sesion or not
     * @param sesion    the Sesion object to save to DB
     * @return          returns false if the Sesion has been saved, but it overlaps with another Sesion on the DB
     */
    public boolean save(Sesion sesion){
        if(sesion.getSala().isEstaOculto())
            throw new InvalidSaveException("Tried to save a new session with a hidden room assigned");

        sesionRepository.save(sesion);
        return checkDateAvailability(sesion);
    }

    private boolean checkDateAvailability(Sesion newSesion){
        //Possible optimization https://stackoverflow.com/questions/22007341/spring-jpa-selecting-specific-columns
        List<Sesion> sesiones = sesionRepository.findAll();
        for (var sesion : sesiones) {
            if(sesion.getId() != newSesion.getId() && checkSesionOverlap(sesion, newSesion))
                return false;
        }
        return true;
    }

    private boolean checkSesionOverlap(Sesion sesion1, Sesion sesion2){
        var startDate1 = sesion1.getFechaIni();
        var endDate1 = DateUtils.addMinutes(sesion1.getFechaIni(), sesion1.getDuracion());
        var startDate2 = sesion2.getFechaIni();
        var endDate2 = DateUtils.addMinutes(sesion2.getFechaIni(), sesion2.getDuracion());
        if((startDate1.before(endDate2) || startDate1.equals(endDate2)) && (endDate1.after(startDate2) || endDate1.equals(startDate2)))
            return true;
        else
            return false;
    }

    public Sesion read(int id){
        return sesionRepository.findById(id).get();
    }

    public void delete(Sesion sesion){
        sesionRepository.delete(sesion);
    }
}
