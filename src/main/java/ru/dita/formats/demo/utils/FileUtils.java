package ru.dita.formats.demo.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import ru.dita.formats.demo.exception.StorageException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    public static File initFolder(String path) {
        var folder = new File(path).getAbsoluteFile();
        try {
            org.apache.commons.io.FileUtils.forceMkdir(folder);
            return folder;
        } catch (IOException e) {
            log.error("Error while creating folder" + folder.getAbsolutePath(), e);
            throw new RuntimeException("Error while creating folder");
        }
    }

    public static Path saveUploadFile(Path rootLocation, MultipartFile file) {
        try {
            if (file.isEmpty() || file.getOriginalFilename() == null) {
                throw new StorageException("Failed to store empty file.");
            }
            var destinationFile = rootLocation.resolve(Paths.get(file.getOriginalFilename()))
                    .normalize()
                    .toAbsolutePath();
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                return destinationFile;
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }
}
