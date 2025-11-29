package com.wedding.rsvp.repository;

import com.wedding.rsvp.model.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    
    /**
     * Buscar invitado con sus adultos cargados (evita N+1 queries)
     */
    @Query("SELECT g FROM Guest g LEFT JOIN FETCH g.adults WHERE g.id = :id")
    Optional<Guest> findByIdWithAdults(Long id);
    
    /**
     * Buscar invitados que asisten
     */
    Page<Guest> findByAttending(Boolean attending, Pageable pageable);
    
    /**
     * Buscar por email de contacto
     */
    Optional<Guest> findByContactEmail(String contactEmail);
}
