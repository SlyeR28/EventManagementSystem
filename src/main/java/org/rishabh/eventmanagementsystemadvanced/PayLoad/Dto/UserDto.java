package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.Role;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto  implements java.io.Serializable {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private Boolean isActive;
    private String activationCode;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
