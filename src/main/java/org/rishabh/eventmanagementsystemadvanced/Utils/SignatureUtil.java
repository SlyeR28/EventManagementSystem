package org.rishabh.eventmanagementsystemadvanced.Utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SignatureUtil {
    public static boolean verifyRazorPaySignature(String signature, String payload , String secret) {
        try{
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] signatureBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String generated = byteToHex(signatureBytes);
            return generated.equals(signature)
                    || generated.equalsIgnoreCase(signature)
                    || Base64.getEncoder().encodeToString(signatureBytes).equals(signature);
        }catch (Exception e){return false;}
    }

    private static String byteToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
