package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Sesion;
import com.daw.ticketsdaw.Repositories.SesionRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@Service
@Transactional
public class SesionService {

    @Autowired
    private SesionRepository sesionRepository;

    public void save(Sesion sesion){
        Date startDate = sesion.getFechaIni();
        int duracionEstandar = sesion.getEvento().getDuracionEstandar();
        Date endDate = DateUtils.addMinutes(sesion.getFechaIni(), duracionEstandar);
        if(sesionRepository.countByFechaIniBetween(startDate, endDate) > 0)
            System.out.println("Sesion save failed");
        else
            sesionRepository.save(sesion);
    }

    private boolean overlap(Date startDate1, int duration1, Date startDate2, int duration2){
        //Implement this logic https://stackoverflow.com/questions/3269434/whats-the-most-efficient-way-to-test-if-two-ranges-overlap
        //(StartA <= EndB) and (EndA >= StartB)
    }

    public Sesion read(int id){
        return sesionRepository.findById(id).get();
    }

    public void delete(Sesion sesion){
        sesionRepository.delete(sesion);
    }
}
