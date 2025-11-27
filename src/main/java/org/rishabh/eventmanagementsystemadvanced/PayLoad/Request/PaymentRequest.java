package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentProviders;

@Data
public class PaymentRequest {

    @NotNull(message = "Order ID is required")
    @Positive(message = "Order ID must be a positive number")
    private Long orderId;

    @NotNull(message = "Payment amount is required ")
    @DecimalMin(value = "0.01" , message = "Amount must be a least 0.01")
    private Double amount; // in major currency units (e.g., INR)

    @NotBlank(message = "Currency cannot be empty")
    @Pattern(regexp = "^[A-Z]{3}$" , message = "Currency must be a valid 3 -letter ISO code")
    private String currency; // e.g., "INR"

    @Size(max = 200 , message = "Description cannot exceed 200 characters")
    private String description;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Payment provider is required")
    private PaymentProviders  paymentProviders;
}
