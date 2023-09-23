package com.example.collections_backend.files;

import com.example.collections_backend.exception_handling.exceptions.FileDeleteFailedException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${upload.path}")
    private String path;


    public byte[] getImageByName(String filename) throws IOException {

        Path destination = Paths.get(path + filename);

        return IOUtils.toByteArray(destination.toUri());
    }

    public List<String> uploadItemImages(List<MultipartFile> images) {

        return images.stream()
                .map(image -> {
                    try {
                        return uploadFile(image);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
        })
                .toList();
    }

    public List<String> changeItemImages(List<String> oldImages, List<String> itemImages) {
        itemImages.removeAll(oldImages);
        if (itemImages.size() != 0) {
            itemImages.forEach(image -> {
                try {
                    deleteImageFromStorage(image);
                } catch (FileNotFoundException e) {
                    throw new FileDeleteFailedException();
                }
            });
        }
        return itemImages;
    }

    public String uploadFile(MultipartFile file) throws IOException {

        String filename = generateFilenameByTime(file);

        file.transferTo( new File( path + filename ) );

        return filename;
    }

    private static String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');

        if ( lastDot == -1 )  // files without extension
            return "";
        else
            return filename.substring( lastDot );
    }

    public void deleteImageFromStorage(String filename) throws FileNotFoundException {
        File file = new File(path + filename);
        if(file.exists()) {
            if(!file.delete()) {
                throw new FileDeleteFailedException();
            }
        }
        else {
            throw new FileNotFoundException();
        }
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
