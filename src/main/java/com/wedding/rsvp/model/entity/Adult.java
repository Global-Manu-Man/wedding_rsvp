package com.wedding.rsvp.model.entity;

import com.wedding.rsvp.model.enums.MenuType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "adults")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;
    
    @Column(length = 500)
    private String allergies;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MenuType menu;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;
    
    @Column(name = "adult_order")
    private Integer adultOrder;
}
