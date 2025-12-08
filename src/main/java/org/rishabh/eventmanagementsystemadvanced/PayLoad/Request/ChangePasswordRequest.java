package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "old password is required")
    private String oldPassword;

    @NotNull(message =  "New password is required")
    @Size(min = 6 , message =  "Password must be at least 6 characters")
    private String newPassword;

    @NotBlank(message = "confirm password is required")
    private String confirmPassword;

}
