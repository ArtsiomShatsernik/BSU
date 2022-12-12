package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UnpackingFactory {
    public static void unpackFile(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        String contentType = Files.probeContentType(filePath);
        switch (contentType) {
            case "application/java-archive":
                ArchivingLib.unpackingJar(fileName);
                break;
            case "application/x-zip-compressed":
                ArchivingLib.unpackingZip(fileName);
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + contentType);
        }
    }
}
