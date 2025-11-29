package com.wedding.rsvp.controller;

import com.wedding.rsvp.model.dto.GuestRequestDTO;
import com.wedding.rsvp.model.dto.GuestResponseDTO;
import com.wedding.rsvp.model.dto.PageResponseDTO;
import com.wedding.rsvp.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/guests")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*") // Permitir CORS para desarrollo (ajustar en producción)
public class GuestController {
    
    private final GuestService guestService;
    
    /**
     * Crear un nuevo invitado (Confirmar asistencia)
     * POST /api/v1/guests
     */
    @PostMapping
    public ResponseEntity<GuestResponseDTO> createGuest(@Valid @RequestBody GuestRequestDTO requestDTO) {
        log.info("Recibida solicitud para crear invitado");
        GuestResponseDTO response = guestService.createGuest(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Obtener un invitado por ID
     * GET /api/v1/guests/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<GuestResponseDTO> getGuestById(@PathVariable Long id) {
        log.info("Recibida solicitud para obtener invitado con ID: {}", id);
        GuestResponseDTO response = guestService.getGuestById(id);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtener todos los invitados con paginación
     * GET /api/v1/guests?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<PageResponseDTO<GuestResponseDTO>> getAllGuests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Recibida solicitud para obtener todos los invitados - Página: {}, Tamaño: {}", page, size);
        PageResponseDTO<GuestResponseDTO> response = guestService.getAllGuests(page, size);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtener invitados que asisten
     * GET /api/v1/guests/attending?page=0&size=10
     */
    @GetMapping("/attending")
    public ResponseEntity<PageResponseDTO<GuestResponseDTO>> getAttendingGuests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Recibida solicitud para obtener invitados que asisten - Página: {}, Tamaño: {}", page, size);
        PageResponseDTO<GuestResponseDTO> response = guestService.getAttendingGuests(page, size);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtener invitados que NO asisten
     * GET /api/v1/guests/not-attending?page=0&size=10
     */
    @GetMapping("/not-attending")
    public ResponseEntity<PageResponseDTO<GuestResponseDTO>> getNotAttendingGuests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Recibida solicitud para obtener invitados que NO asisten - Página: {}, Tamaño: {}", page, size);
        PageResponseDTO<GuestResponseDTO> response = guestService.getNotAttendingGuests(page, size);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtener estadísticas de invitados
     * GET /api/v1/guests/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getGuestStats() {
        log.info("Recibida solicitud para obtener estadísticas de invitados");
        
        long totalGuests = guestService.countTotalGuests();
        long attendingGuests = guestService.countAttendingGuests();
        long notAttendingGuests = totalGuests - attendingGuests;
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalGuests", totalGuests);
        stats.put("attendingGuests", attendingGuests);
        stats.put("notAttendingGuests", notAttendingGuests);
        stats.put("attendanceRate", totalGuests > 0 ? 
            String.format("%.2f%%", (attendingGuests * 100.0 / totalGuests)) : "0%");
        
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Health check endpoint
     * GET /api/v1/guests/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Wedding RSVP Service");
        return ResponseEntity.ok(health);
    }
}
