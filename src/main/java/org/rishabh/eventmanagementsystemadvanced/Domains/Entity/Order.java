package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.OrderStatus;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "ticket_order")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime createdAt;

}

