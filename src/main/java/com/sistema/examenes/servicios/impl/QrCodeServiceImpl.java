package com.sistema.examenes.servicios.impl;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sistema.examenes.entidades.RegistroAsistenciaQR;
import com.sistema.examenes.repositorios.QrCodeRepository;
import com.sistema.examenes.servicios.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Override
    public byte[] generateQRCode(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el código QR", e);
        }
    }

    @Override
    public void registrarEscaneo(String username, String estado) {
        RegistroAsistenciaQR registro = new RegistroAsistenciaQR();
        registro.setUsername(username);
        registro.setEstado(estado);
        registro.setFechaEscaneo(LocalDateTime.now());
        qrCodeRepository.save(registro);
    }
}
