package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Entities.Evento;
import com.daw.ticketsdaw.Entities.RecursoMedia;
import com.daw.ticketsdaw.Repositories.RecursoMediaRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
@Transactional
public class RecursoMediaService extends AbstractFileService{
    @Autowired
    RecursoMediaRepository mediaRepository;
    @Autowired
    Environment environment;

    public RecursoMedia createFromFile(MultipartFile multipartFile) throws IOException {
        String fileName = generateSafeFileName(multipartFile.getOriginalFilename());

        RecursoMedia recursoMedia = new RecursoMedia();
        recursoMedia.setPrioridad(0);

        if (!StringUtils.substringAfterLast(fileName,".").equals("pdf")){
            recursoMedia.setNombreArchivo(addWebpExtension(fileName));
        } else {
            recursoMedia.setNombreArchivo(fileName);
        }

        mediaRepository.save(recursoMedia);

        saveMultipartAs(multipartFile, fileName);
        return recursoMedia;
    }

    public RecursoMedia saveImageGallery(MultipartFile multipartFile, RecursoMedia baseRecursoMedia, Evento evento) throws IOException {
        String fileName = generateSafeFileName(multipartFile.getOriginalFilename());

        RecursoMedia recursoMedia = baseRecursoMedia;

        if (!StringUtils.substringAfterLast(fileName,".").equals("pdf")){
            recursoMedia.setNombreArchivo(addWebpExtension(fileName));
        } else {
            recursoMedia.setNombreArchivo(fileName);
        }

        recursoMedia.setEventoGaleriaImagenes(evento);
        mediaRepository.save(recursoMedia);
        saveMultipartAs(multipartFile, fileName);

        return recursoMedia;
    }

    public RecursoMedia read(int id){
        return mediaRepository.findById(id).get();
    }

    public void save(RecursoMedia recursoMedia){
        mediaRepository.save(recursoMedia);
    }

    public void delete(RecursoMedia recursoMedia){
        mediaRepository.delete(recursoMedia);
    }

    @Override
    boolean checkFileExt(String fileExt) {
        final String[] validExtensions = {"png", "jpg", "webp"};
        return Arrays.stream(validExtensions).anyMatch(fileExt::equals);
    }

}
