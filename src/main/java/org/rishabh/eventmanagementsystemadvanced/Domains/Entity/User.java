package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen" , sequenceName = "user_seq" , initialValue = 1000 ,allocationSize = 10)
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

    @Column(length = 100)
    private String activationCode;

    private Boolean isActive;



    @OneToOne(cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Images profileImage;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
    private List<Event> organizedEvents = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "user_attending_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> attendingEvents = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "user_staffing_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> staffingEvents = new ArrayList<>();

    private String profilePictureUrl;
    private String publicId;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


    @PrePersist
    public void prePersist() {
        // Set default role if not assigned
        if (role == null) {
            role = Role.ATTENDEE;
        }
        if(this.isActive == null){
            isActive = false;
        }
    }
    
}
