package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.Role;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , length = 50)
    private String fullName;

    @Column(nullable = false , length = 100 , unique = true)
    private String email;

    @Column(nullable = false , length = 150)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @Column(length = 500)
    private String imageUrl;

    private String publicId;

    @Column(length = 100)
    private String activationCode;

    private Boolean isActive;

    @PrePersist
    public void prePersist() {
        // Set default role if not assigned
        if (role == null) {
            role = Role.USER;
        }
        if(this.isActive == null){
            isActive = false;
        }
    }

}
