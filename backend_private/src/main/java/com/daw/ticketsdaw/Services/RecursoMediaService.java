package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.RecursoMedia;
import com.daw.ticketsdaw.Repositories.RecursoMediaRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
@Transactional
public class RecursoMediaService extends AbstractFileService{
    @Autowired
    RecursoMediaRepository mediaRepository;



    public RecursoMedia createFromFile(MultipartFile multipartFile) throws IOException {
        String fileName = generateSafeFileName(multipartFile.getOriginalFilename());

        RecursoMedia recursoMedia = new RecursoMedia();
        recursoMedia.setPrioridad(0);
        recursoMedia.setNombreArchivo(fileName);

        mediaRepository.save(recursoMedia);

        saveMultipartAs(multipartFile, fileName);

        return recursoMedia;
    }

    public RecursoMedia read(int id){
        return mediaRepository.findById(id).get();
    }

    @Override
    boolean checkFileExt(String fileExt) {
        final String[] validExtensions = {"png", "jpg", "webp"};
        return Arrays.stream(validExtensions).anyMatch(fileExt::equals);
    }
}
