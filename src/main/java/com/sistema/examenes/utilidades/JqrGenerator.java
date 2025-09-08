package com.sistema.examenes.utilidades;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class JqrGenerator {

    public BufferedImage generarImagenQR(String contenido) throws WriterException {
        int width = 250;
        int height = 250;
        BitMatrix matrix = new MultiFormatWriter().encode(contenido, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }
}
