package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.RecursoMedia;
import com.daw.ticketsdaw.Repositories.RecursoMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecursoMediaService {
    @Autowired
    RecursoMediaRepository mediaRepository;

    public RecursoMedia read(int id){
        return mediaRepository.findById(id).get();
    }

    public void save(RecursoMedia media){
        //Tratar ficheros aqui
        mediaRepository.save(media);
    }

    public void remove(RecursoMedia media){
        //Tratar ficheros aqui
        mediaRepository.delete(media);
    }
}
