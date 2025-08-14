package com.sistema.examenes.servicios;

public interface QrCodeService {
    byte[] generateQRCode(String text, int width, int height);
    void registrarEscaneo(String username, String estado);
}
