package com.wedding.rsvp.repository;

import com.wedding.rsvp.model.entity.Adult;
import com.wedding.rsvp.model.enums.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdultRepository extends JpaRepository<Adult, Long> {
    
    /**
     * Buscar adultos por guest ID
     */
    List<Adult> findByGuestId(Long guestId);
    
    /**
     * Contar adultos por tipo de menú
     */
    long countByMenu(MenuType menu);
    
    /**
     * Buscar adultos con alergias
     */
    List<Adult> findByAllergiesIsNotNull();
}
