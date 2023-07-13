package com.example.collections_backend.files;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
public class FileService {
    @Value("${upload.path}")
    private String path;


    public byte[] getImageByName(String imageName) throws IOException {

        Path destination = Paths.get(path + imageName);

        return IOUtils.toByteArray(destination.toUri());
    }

    public String uploadFile(MultipartFile file) throws IOException {

        String filename = generateFilenameByTime(file);

        file.transferTo( new File( path + filename ) );

        return filename;
    }

    private static String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');

        if ( lastDot == -1 )  // files without extension
            return "";
        else
            return fileName.substring( lastDot );
    }

    private static String generateFilenameFunction() {
        StringBuilder code = new StringBuilder();
        int r;
        Random rand = new Random();
        for (int i = 0; i <= 10 ; i++) {
            r = rand.nextInt(9);
            switch (r) {
                case 1, 4, 7 -> code.append((char) (rand.nextInt(65, 91)));
                case 2, 5 -> code.append((char) (rand.nextInt(48, 58)));
                case 3, 6, 8 -> code.append((char) (rand.nextInt(97, 123)));
            }
        }
        return code.toString();
    }

    private static String generateFilenameByTime(MultipartFile file){
        return System.currentTimeMillis() + generateFilenameFunction() + getFileExtension(file.getOriginalFilename());
    }
}
