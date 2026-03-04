package com.pasiontuerca.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service("localStorageService")
public class LocalStorageService implements StorageService {

    @Value("${pasion.uploads-dir:uploads}")
    private String uploadsDir;

    @Override
    public String store(MultipartFile file) throws IOException {
        // Clean the original filename from the client
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

        // Extract the file extension (e.g., "jpeg")
        String extension = StringUtils.getFilenameExtension(originalFilename);

        // Generate a unique, safe filename to store
        String storedFilename = UUID.randomUUID().toString() + "." + extension;

        Path uploadPath = Path.of(uploadsDir);

        // Ensure the upload directory exists
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Resolve the final path and copy the file
        Path destinationFile = uploadPath.resolve(storedFilename);
        Files.copy(file.getInputStream(), destinationFile);

        // Return the public URL for the newly stored file
        return "/uploads/" + storedFilename;
    }

    @Override
    public void delete(String url) throws IOException {
        if (url == null || !url.startsWith("/uploads/")) {
            return;
        }
        String filename = url.substring("/uploads/".length());
        Path filePath = Path.of(uploadsDir).resolve(filename);
        Files.deleteIfExists(filePath);
    }
}
