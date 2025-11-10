package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;

import java.util.Set;


@Entity
@Table(name = "notification_templates")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @ElementCollection(targetClass = ChannelType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "template_channels", joinColumns = @JoinColumn(name = "template_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type", nullable = false)
    private Set<ChannelType> channels;
}