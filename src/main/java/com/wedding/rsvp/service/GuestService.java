package com.wedding.rsvp.service;

import com.wedding.rsvp.model.dto.GuestRequestDTO;
import com.wedding.rsvp.model.dto.GuestResponseDTO;
import com.wedding.rsvp.model.dto.PageResponseDTO;

public interface GuestService {
    
    /**
     * Crear un nuevo invitado (RSVP)
     */
    GuestResponseDTO createGuest(GuestRequestDTO requestDTO);
    
    /**
     * Obtener un invitado por ID
     */
    GuestResponseDTO getGuestById(Long id);
    
    /**
     * Obtener todos los invitados con paginación
     */
    PageResponseDTO<GuestResponseDTO> getAllGuests(int page, int size);
    
    /**
     * Obtener invitados que asisten con paginación
     */
    PageResponseDTO<GuestResponseDTO> getAttendingGuests(int page, int size);
    
    /**
     * Obtener invitados que NO asisten con paginación
     */
    PageResponseDTO<GuestResponseDTO> getNotAttendingGuests(int page, int size);
    
    /**
     * Contar total de invitados
     */
    long countTotalGuests();
    
    /**
     * Contar invitados que asisten
     */
    long countAttendingGuests();
}
