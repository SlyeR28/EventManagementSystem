package org.rishabh.eventmanagementsystemadvanced.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "payment.razorpay")
@Data
public class RazorPayConfig {

    @Value("${razorpay.api.key}")
    private  String key;
    @Value("${razorpay.api.secret}")
    private  String secret;
}
