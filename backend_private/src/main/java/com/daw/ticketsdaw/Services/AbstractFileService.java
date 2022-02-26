package com.daw.ticketsdaw.Services;

import com.daw.ticketsdaw.Exceptions.IncorrectFileExtensionException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
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
        String fileExt =  StringUtils.substringAfterLast(fileName, ".");

        if (fileExt.equals("pdf")){
            multipartFile.transferTo(path);
        } else {

            String newFileName = StringUtils.substringBeforeLast(fileName, ".") + ".webp";
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getResource().getInputStream());

            ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(fileExt).next();
            ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(0.3f);

            imageWriter.write(null,new IIOImage(bufferedImage,null,null),imageWriteParam);

            File output = new File(environment.getProperty("tickets.uploads.path") + newFileName);

            ImageIO.write(bufferedImage, "webp", output);
            output.createNewFile();
        }
    }

    abstract boolean checkFileExt(String fileExt);
}
