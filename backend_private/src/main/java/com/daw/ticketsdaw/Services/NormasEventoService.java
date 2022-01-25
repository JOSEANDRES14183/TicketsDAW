package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.NormasEvento;
import com.daw.ticketsdaw.Entities.RecursoMedia;
import com.daw.ticketsdaw.Repositories.NormasEventoRepository;
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
public class NormasEventoService {
    @Autowired
    NormasEventoRepository normasRepository;

    @Autowired
    Environment environment;

    public NormasEvento createFromFile(MultipartFile multipartFile) throws IOException {
        //TODO: GENERATE RANDOM STRING OR SANITIZE USER INPUT AND REFACTOR
        String fileName = multipartFile.getOriginalFilename();

        NormasEvento normasEvento = new NormasEvento();
        normasEvento.setNombrePdf(fileName);

        normasRepository.save(normasEvento);

        Path path = Paths.get(environment.getProperty("tickets.uploads.path") + fileName);
        multipartFile.transferTo(path);

        return normasEvento;
    }
}
