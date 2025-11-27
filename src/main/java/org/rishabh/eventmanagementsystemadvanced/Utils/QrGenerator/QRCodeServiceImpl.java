package org.rishabh.eventmanagementsystemadvanced.Utils.QrGenerator;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Services.ImageBase;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class QRCodeServiceImpl extends ImageBase implements QRCodeService {

    public QRCodeServiceImpl(Cloudinary cloudinary) {
        super(cloudinary);
    }

    @Override
    public String generateQR(String content) {
        try {


            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] qrBytes = pngOutputStream.toByteArray();


            String publicId = "qr_" + UUID.randomUUID();


            Map uploadResult = super.cloudinary.uploader().upload(
                    qrBytes,
                    ObjectUtils.asMap(
                            "folder", "qr-codes",
                            "public_id", publicId
                    )
            );

            // 4️⃣ Create metadata record (if needed to store in DB later)
            ImageInfo imageInfo = new ImageInfo(
                    uploadResult.get("public_id").toString(),
                    uploadResult.get("secure_url").toString(),
                    uploadResult.get("format").toString(),
                    LocalDateTime.now()
            );


            return imageInfo.securedUrl();

        } catch (WriterException e) {

            throw new RuntimeException("Error generating QR code");
        } catch (Exception e) {

            throw new RuntimeException("Error uploading QR code");
        }
    }
}
