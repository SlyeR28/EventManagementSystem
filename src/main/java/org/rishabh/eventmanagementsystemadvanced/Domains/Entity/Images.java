package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "Images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "image_seq")
    @SequenceGenerator(name = "image_url" , sequenceName = "image_seq" , initialValue = 2000 , allocationSize = 10)
    private Long id;

    private String securedUrl;

    private String publicId;

    private String folder;

    private String format;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime uploadedAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
