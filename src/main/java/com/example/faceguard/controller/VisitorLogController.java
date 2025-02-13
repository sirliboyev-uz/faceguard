package com.example.faceguard.controller;

import com.example.faceguard.dto.ImageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class VisitorLogController {

    @PostMapping("/visitor")
    public ResponseEntity<?> logVisitor(@RequestBody ImageRequest request) {
        String imageData = request.getImage();
        if (imageData == null || imageData.isEmpty()) {
            return ResponseEntity.badRequest().body("No image provided.");
        }
        try {
            // Remove data URL header if present.
            String base64Image;
            if (imageData.contains("base64,")) {
                base64Image = imageData.substring(imageData.indexOf("base64,") + 7);
            } else {
                base64Image = imageData;
            }
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // Generate a unique visitor ID.
            String visitorId = "visitor_" + UUID.randomUUID().toString();

            // Define the folder where screenshots will be saved.
            Path folderPath = Paths.get("uploads/visitors");
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            // Save the image to the folder with the visitor ID as filename.
            String filename = visitorId + ".jpg";
            Path filePath = folderPath.resolve(filename);
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(imageBytes);
            }

            // Log visitor information (you might also record a timestamp in a real system)
            System.out.println("Logged visitor: " + visitorId + " at " + filePath.toString());

            // Return the visitor ID as a response.
            return ResponseEntity.ok("Visitor logged with ID: " + visitorId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error logging visitor: " + e.getMessage());
        }
    }
}
