package com.example.faceguard.controller;

import jakarta.annotation.PostConstruct;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import nu.pattern.OpenCV;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.opencv.videoio.VideoCapture;

@RestController
public class VideoStreamController {

    private CascadeClassifier faceDetector;

    @PostConstruct
    public void init() {
        // Use nu.pattern's loader to load the native OpenCV library.
        OpenCV.loadShared();
        System.out.println("Loaded OpenCV: " + Core.VERSION);

        // Load the Haar cascade file from resources.
        try (InputStream is = getClass().getResourceAsStream("/haarcascade_frontalface_default.xml")) {
            if (is == null) {
                throw new RuntimeException("Haar cascade file not found in resources.");
            }
            // Write the file to a temporary file so that CascadeClassifier can read it.
            File tempFile = File.createTempFile("haarcascade", ".xml");
            try (FileOutputStream os = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
            faceDetector = new CascadeClassifier(tempFile.getAbsolutePath());
            tempFile.deleteOnExit();
            System.out.println("Face detector loaded.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/stream", produces = "multipart/x-mixed-replace;boundary=frame")
    public ResponseEntity<StreamingResponseBody> streamVideo() {
        StreamingResponseBody responseBody = outputStream -> {
            // Open the video source. Use 0 for webcam; replace with RTSP URL if needed.
            VideoCapture capture = new VideoCapture(0);
            // For RTSP stream, use: new VideoCapture("rtsp://your-camera-url")
            if (!capture.isOpened()) {
                System.err.println("Could not open video capture.");
                return;
            }

            Mat frame = new Mat();
            Mat gray = new Mat();
            while (capture.read(frame)) {
                // Convert to grayscale for detection.
                Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
                // Detect faces.
                MatOfRect faceDetections = new MatOfRect();
                faceDetector.detectMultiScale(gray, faceDetections);
                // Draw green rectangles around detected faces.
                for (Rect rect : faceDetections.toArray()) {
                    Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 255, 0), 2);
                }
                // Encode the frame as JPEG.
                MatOfByte mob = new MatOfByte();
                Imgcodecs.imencode(".jpg", frame, mob);
                byte[] imageBytes = mob.toArray();

                // Write the multipart response (MJPEG).
                outputStream.write(("--frame\r\nContent-Type: image/jpeg\r\nContent-Length: "
                        + imageBytes.length + "\r\n\r\n").getBytes());
                outputStream.write(imageBytes);
                outputStream.write("\r\n".getBytes());
                outputStream.flush();

                // Control frame rate (e.g., 30 FPS ~ 33 ms per frame)
                try {
                    Thread.sleep(33);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            capture.release();
        };

        return ResponseEntity.status(HttpStatus.OK)
                .header("Cache-Control", "no-cache")
                .contentType(MediaType.parseMediaType("multipart/x-mixed-replace;boundary=frame"))
                .body(responseBody);
    }
}
