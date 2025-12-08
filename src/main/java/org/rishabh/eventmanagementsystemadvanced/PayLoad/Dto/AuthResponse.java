package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String email;
    private String token;
    private Long userId;
    private String role;
    private String fullName;

}
