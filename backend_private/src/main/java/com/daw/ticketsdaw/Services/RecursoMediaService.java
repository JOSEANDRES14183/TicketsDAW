package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.RecursoMedia;
import com.daw.ticketsdaw.Repositories.RecursoMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class RecursoMediaService {
    @Autowired
    RecursoMediaRepository mediaRepository;

    @Autowired
    Environment environment;

    public RecursoMedia createFromFile(MultipartFile multipartFile) throws IOException {
        //TODO: GENERATE RANDOM STRING OR SANITIZE USER INPUT AND REFACTOR
        String fileName = multipartFile.getOriginalFilename();

        RecursoMedia recursoMedia = new RecursoMedia();
        recursoMedia.setPrioridad(0);
        recursoMedia.setNombreArchivo(fileName);

        mediaRepository.save(recursoMedia);

        Path path = Paths.get(environment.getProperty("tickets.uploads.path") + fileName);
        multipartFile.transferTo(path);

        return recursoMedia;
    }

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
