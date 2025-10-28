package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "Tcket_type")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double  basePrice;

    @Column(nullable = false)
    private Integer totalQuantity;

    @Column(nullable = false)
    private Integer remainingQuantity;


    private Double currentPrice;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "ticketType" , cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    //-------Utility Methods-----//

    public void sellTicket(int quantity) {
        if (remainingQuantity < quantity) {
            throw new IllegalStateException("Not enough tickets available for " + name);
        }
        remainingQuantity -= quantity;
    }


    public void resetAvailability() {
        this.remainingQuantity = this.totalQuantity;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (remainingQuantity == 0) remainingQuantity = totalQuantity;
        if (currentPrice == 0) currentPrice = basePrice;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
