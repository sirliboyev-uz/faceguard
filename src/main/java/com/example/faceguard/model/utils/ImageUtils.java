// src/main/java/com/example/demo/util/ImageUtils.java
package com.example.faceguard.model.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class ImageUtils {

    public static Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        int[] data = new int[bi.getWidth() * bi.getHeight()];
        bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), data, 0, bi.getWidth());
        byte[] bytes = new byte[bi.getWidth() * bi.getHeight() * 3];
        for (int i = 0; i < data.length; i++) {
            int argb = data[i];
            bytes[i * 3] = (byte)((argb >> 16) & 0xFF);     // R
            bytes[i * 3 + 1] = (byte)((argb >> 8) & 0xFF);    // G
            bytes[i * 3 + 2] = (byte)(argb & 0xFF);           // B
        }
        mat.put(0, 0, bytes);
        return mat;
    }

    public static BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage bi = new BufferedImage(mat.cols(), mat.rows(), type);
        mat.get(0, 0, ((DataBufferByte) bi.getRaster().getDataBuffer()).getData());
        return bi;
    }
}
