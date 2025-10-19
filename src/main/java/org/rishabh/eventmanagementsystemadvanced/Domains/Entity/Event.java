package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.EventStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100 , nullable = false)
    private String name;

    @Column(length = 200 , nullable = false)
    private String venue;

    @Lob
    private String description;

    @Column(name = "start")
    private LocalDateTime startTime;

    @Column(name = "end")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
     @Column(name = "Status")
    private EventStatus status;

    @Column(name="Sales_Start")
    private LocalDateTime salesStartTime;

    @Column(name="Sales_end")
    private LocalDateTime salesEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private User  organizer;

    @ManyToMany(mappedBy = "attendingEvents")
    private List<User>attendees = new ArrayList<>();

    @ManyToMany(mappedBy = "staffingEvents")
    private List<User>staff = new ArrayList<>();

    @OneToMany(mappedBy = "event" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Images> eventImages = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
