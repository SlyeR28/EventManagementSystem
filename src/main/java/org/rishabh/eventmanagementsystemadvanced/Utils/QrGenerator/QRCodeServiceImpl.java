package org.rishabh.eventmanagementsystemadvanced.Utils.QrGenerator;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@Service
public class QRCodeServiceImpl implements QRCodeService {


    private static final String QR_CODE_DIR = "uploads/qrcodes/";

    @Override
    public String generateQR(String content) {

        try {
            File dir = new File(QR_CODE_DIR);
            if (!dir.exists()) dir.mkdirs();

            String fileName = UUID.randomUUID() + ".png";
            String filePath = QR_CODE_DIR + fileName;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300);

            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            log.info("✅ QR Code generated for ticket: {}", content);
            return "/qrcodes/" + fileName; // URL to be served by frontend/static handler

        } catch (WriterException | IOException e) {
            log.error("❌ Failed to generate QR code", e);
            throw new RuntimeException("Error generating QR code");
        }

    }


    //    public static String generateQrCode(String text , String filePath) {
//        try{
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix bitMatrix = qrCodeWriter.encode(text , BarcodeFormat.QR_CODE , 300 , 300);
//
//            Path path = FileSystems.getDefault().getPath(filePath);
//            MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
//            return filePath;
//        }catch (WriterException | IOException e){
//            throw new RuntimeException("Error generating QR code");
//        }
//    }

}
