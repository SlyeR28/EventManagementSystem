package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.EventStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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


    //Relation with User as Organizer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private User organizer;


    // Relation with User as attendees who attend's event
    @ManyToMany(mappedBy = "attendingEvents")
    private List<User>attendees= new ArrayList<>();

    //Relation with User as Working staff who ar managing the event
    @ManyToMany(mappedBy = "staffingEvents")
    private List<User> staff = new ArrayList<>();

    // Relation with TicketType
    @OneToMany(mappedBy = "event" ,  cascade = CascadeType.ALL)
    private List<TicketType>ticketTypes = new ArrayList<>();


    //relation with category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "event" ,  cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Images>images = new ArrayList<>();



    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
