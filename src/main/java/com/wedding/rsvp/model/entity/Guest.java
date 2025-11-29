package com.wedding.rsvp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Boolean attending;
    
    @Column(name = "number_of_adults", nullable = false)
    private Integer numberOfAdults;
    
    @Column(name = "number_of_children", nullable = false)
    private Integer numberOfChildren;
    
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Adult> adults = new ArrayList<>();
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "contact_email")
    private String contactEmail;
    
    @Column(name = "contact_phone")
    private String contactPhone;
    
    @Column(length = 500)
    private String notes;
    
    // Helper methods para manejar la relación bidireccional
    public void addAdult(Adult adult) {
        adults.add(adult);
        adult.setGuest(this);
    }
    
    public void removeAdult(Adult adult) {
        adults.remove(adult);
        adult.setGuest(null);
    }
}
