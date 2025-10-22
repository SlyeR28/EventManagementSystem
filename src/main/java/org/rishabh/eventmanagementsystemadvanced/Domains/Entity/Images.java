package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "image_seq")
    @SequenceGenerator(name = "image_url" , sequenceName = "image_seq" , initialValue = 2000 , allocationSize = 10)
    private Long id;

    private String imageUrl;

    private String publicId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne(mappedBy = "profileImage")
    private User user;





}
