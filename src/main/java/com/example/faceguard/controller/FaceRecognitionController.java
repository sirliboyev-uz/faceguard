//package com.example.faceguard.controller;
//
//import ai.onnxruntime.*;
//import com.example.faceguard.dto.ImageRequest;
//import jakarta.annotation.PostConstruct;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.nio.FloatBuffer;
//import java.util.Base64;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/face")
//public class FaceRecognitionController {
//
//    private OrtEnvironment env;
//    private OrtSession session;
//    // Define input dimensions according to your converted InsightFace ONNX model.
//    private static final int INPUT_WIDTH = 112;
//    private static final int INPUT_HEIGHT = 112;
//    private static final int CHANNELS = 3;
//
//    // In a production system, employee embeddings would be loaded from a database.
//    // For demonstration, we'll use a dummy method.
//    private Map<String, float[]> employeeEmbeddings = new HashMap<>();
//
//    @PostConstruct
//    public void init() throws Exception {
//        // Initialize ONNX Runtime environment.
//        env = OrtEnvironment.getEnvironment();
//        OrtSession.SessionOptions options = new OrtSession.SessionOptions();
//        // For GPU acceleration, you would configure CUDA options here if available.
//        // For example: options.addCUDA(); // (Requires proper CUDA setup with ONNX Runtime)
//        // Load your converted InsightFace model. Ensure the path is accessible.
//        String modelPath = "C:/path/to/insightface.onnx";  // Adjust to your model’s location.
//        session = env.createSession(modelPath, options);
//
//        // Load or define dummy employee embeddings.
//        // In a real system, query your employee database and compute embeddings.
//        employeeEmbeddings.put("John Doe", new float[]{ /* embedding vector */ });
//        // ... add more as needed.
//    }
//
//    @PostMapping("/recognize")
//    public ResponseEntity<?> recognizeFace(@RequestBody ImageRequest request) {
//        try {
//            // Decode the Base64 image.
//            String imageData = request.getImage();
//            String base64Image = imageData.contains("base64,") ?
//                    imageData.substring(imageData.indexOf("base64,") + 7) : imageData;
//            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//            InputStream in = new ByteArrayInputStream(imageBytes);
//            BufferedImage img = ImageIO.read(in);
//            if (img == null) {
//                return ResponseEntity.badRequest().body("Invalid image data.");
//            }
//
//            // Preprocess: Resize the image to INPUT_WIDTH x INPUT_HEIGHT.
//            BufferedImage resized = new BufferedImage(INPUT_WIDTH, INPUT_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
//            Graphics2D g = resized.createGraphics();
//            g.drawImage(img, 0, 0, INPUT_WIDTH, INPUT_HEIGHT, null);
//            g.dispose();
//
//            // Convert image to a float array.
//            float[] inputData = imageToFloatArray(resized);
//
//            // Create an ONNX tensor from the float array.
//            OnnxTensor inputTensor = OnnxTensor.createTensor(env, FloatBuffer.wrap(inputData),
//                    new long[]{1, CHANNELS, INPUT_HEIGHT, INPUT_WIDTH});
//
//            // Run inference.
//            OrtSession.Result result = session.run(Collections.singletonMap("input", inputTensor));
//            float[] embedding = extractEmbedding(result);
//
//            // Compare embedding to employee embeddings.
//            String recognizedName = matchEmployee(embedding);
//            if (recognizedName == null) {
//                recognizedName = "Visitor_" + System.currentTimeMillis();
//            }
//
//            // Optionally, annotate the image with the recognized name.
//            BufferedImage annotated = annotateImage(img, recognizedName);
//
//            // (For demonstration, we could write annotated image to disk or return it as Base64.)
//            // Here we encode annotated image to Base64.
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(annotated, "jpg", baos);
//            String annotatedImageBase64 = "data:image/jpeg;base64," +
//                    Base64.getEncoder().encodeToString(baos.toByteArray());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("name", recognizedName);
//            response.put("annotatedImage", annotatedImageBase64);
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error processing image: " + e.getMessage());
//        }
//    }
//
//    // Convert BufferedImage to a float array (example for BGR order, normalized to [0, 1])
//    private float[] imageToFloatArray(BufferedImage img) {
//        int width = img.getWidth();
//        int height = img.getHeight();
//        float[] data = new float[CHANNELS * width * height];
//        int[] rgbArray = new int[width * height];
//        img.getRGB(0, 0, width, height, rgbArray, 0, width);
//        // OpenCV uses BGR order.
//        for (int i = 0; i < rgbArray.length; i++) {
//            int argb = rgbArray[i];
//            // Extract color channels.
//            int r = (argb >> 16) & 0xFF;
//            int g = (argb >> 8) & 0xFF;
//            int b = (argb) & 0xFF;
//            // Normalize values.
//            data[i] = b / 255.0f;                  // Blue channel first.
//            data[i + width * height] = g / 255.0f;   // Then green.
//            data[i + 2 * width * height] = r / 255.0f; // Then red.
//        }
//        return data;
//    }
//
//    // Dummy method: Extract embedding from the ONNX result.
//    private float[] extractEmbedding(OrtSession.Result result) throws OrtException {
//        // Assuming the model outputs a tensor named "output" with shape [1, embedding_dim].
//        OnnxValue outputValue = result.get(0);
//        float[][] embeddings = (float[][]) outputValue.getValue();
//        return embeddings[0];
//    }
//
//    // Dummy method: Compare embedding with stored employee embeddings.
//    // Returns the employee name if a match is found, or null if not.
//    private String matchEmployee(float[] embedding) {
//        // For demonstration, we'll simply return null.
//        // In production, implement cosine similarity or another metric to compare embeddings.
//        return null;
//    }
//
//    // Annotate the original image with the recognized name.
//    private BufferedImage annotateImage(BufferedImage img, String name) {
//        BufferedImage annotated = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
//        Graphics2D g2d = annotated.createGraphics();
//        g2d.drawImage(img, 0, 0, null);
//        g2d.setColor(Color.GREEN);
//        g2d.setFont(new Font("Arial", Font.BOLD, 32));
//        g2d.drawString(name, 20, 40);
//        g2d.dispose();
//        return annotated;
//    }
//}
