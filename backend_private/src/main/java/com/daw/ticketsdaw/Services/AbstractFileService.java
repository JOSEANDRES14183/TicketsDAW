package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Exceptions.IncorrectFileExtensionException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractFileService {
    @Autowired
    Environment environment;

    protected String generateSafeFileName(String originalFileName){
        String fileExt = StringUtils.substringAfterLast(originalFileName, ".");
        if(!checkFileExt(fileExt))
            throw new IncorrectFileExtensionException("Invalid file extension: " + fileExt);
        return RandomStringUtils.randomAlphanumeric(15) + "." + fileExt;
    }

    protected void saveMultipartAs(MultipartFile multipartFile, String fileName) throws IOException {
        Path path = Paths.get(environment.getProperty("tickets.uploads.path") + fileName);
        multipartFile.transferTo(path);
    }

    abstract boolean checkFileExt(String fileExt);
}
