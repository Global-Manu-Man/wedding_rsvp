package com.wedding.rsvp.service.impl;

import com.wedding.rsvp.exception.BusinessValidationException;
import com.wedding.rsvp.exception.ResourceNotFoundException;
import com.wedding.rsvp.mapper.GuestMapper;
import com.wedding.rsvp.model.dto.GuestRequestDTO;
import com.wedding.rsvp.model.dto.GuestResponseDTO;
import com.wedding.rsvp.model.dto.PageResponseDTO;
import com.wedding.rsvp.model.entity.Guest;
import com.wedding.rsvp.repository.GuestRepository;
import com.wedding.rsvp.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestServiceImpl implements GuestService {
    
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;
    
    @Override
    @Transactional
    public GuestResponseDTO createGuest(GuestRequestDTO requestDTO) {
        log.info("Creando nuevo invitado");
        
        // Validaciones de negocio
        validateGuestRequest(requestDTO);
        
        // Convertir DTO a Entity
        Guest guest = guestMapper.toEntity(requestDTO);
        
        // Guardar en base de datos
        Guest savedGuest = guestRepository.save(guest);
        log.info("Invitado creado exitosamente con ID: {}", savedGuest.getId());
        
        // Convertir Entity a DTO de respuesta
        return guestMapper.toResponseDTO(savedGuest);
    }
    
    @Override
    @Transactional(readOnly = true)
    public GuestResponseDTO getGuestById(Long id) {
        log.info("Buscando invitado con ID: {}", id);
        
        Guest guest = guestRepository.findByIdWithAdults(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitado", "id", id));
        
        return guestMapper.toResponseDTO(guest);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<GuestResponseDTO> getAllGuests(int page, int size) {
        log.info("Obteniendo todos los invitados - Página: {}, Tamaño: {}", page, size);
        
        validatePaginationParams(page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Guest> guestPage = guestRepository.findAll(pageable);
        
        return buildPageResponse(guestPage);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<GuestResponseDTO> getAttendingGuests(int page, int size) {
        log.info("Obteniendo invitados que asisten - Página: {}, Tamaño: {}", page, size);
        
        validatePaginationParams(page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Guest> guestPage = guestRepository.findByAttending(true, pageable);
        
        return buildPageResponse(guestPage);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<GuestResponseDTO> getNotAttendingGuests(int page, int size) {
        log.info("Obteniendo invitados que NO asisten - Página: {}, Tamaño: {}", page, size);
        
        validatePaginationParams(page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Guest> guestPage = guestRepository.findByAttending(false, pageable);
        
        return buildPageResponse(guestPage);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countTotalGuests() {
        return guestRepository.count();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countAttendingGuests() {
        return guestRepository.findByAttending(true, Pageable.unpaged()).getTotalElements();
    }
    
    /**
     * Valida los parámetros de paginación
     */
    private void validatePaginationParams(int page, int size) {
        if (page < 0) {
            throw new BusinessValidationException("El número de página no puede ser negativo");
        }
        if (size <= 0) {
            throw new BusinessValidationException("El tamaño de página debe ser mayor a 0");
        }
        if (size > 100) {
            throw new BusinessValidationException("El tamaño de página no puede exceder 100");
        }
    }
    
    /**
     * Valida la solicitud de creación de invitado
     */
    private void validateGuestRequest(GuestRequestDTO requestDTO) {
        // Validar que el número de adultos coincida con la lista
        if (requestDTO.getAdults().size() != requestDTO.getNumberOfAdults()) {
            throw new BusinessValidationException(
                String.format("El número de adultos (%d) no coincide con la cantidad de adultos en la lista (%d)",
                    requestDTO.getNumberOfAdults(),
                    requestDTO.getAdults().size())
            );
        }
        
        // Validar que si asiste, debe haber al menos un adulto
        if (requestDTO.getAttending() && requestDTO.getNumberOfAdults() == 0) {
            throw new BusinessValidationException(
                "Si confirma asistencia, debe registrar al menos un adulto"
            );
        }
    }
    
    /**
     * Construye la respuesta paginada
     */
    private PageResponseDTO<GuestResponseDTO> buildPageResponse(Page<Guest> guestPage) {
        List<GuestResponseDTO> content = guestPage.getContent()
                .stream()
                .map(guestMapper::toResponseDTO)
                .collect(Collectors.toList());
        
        return PageResponseDTO.<GuestResponseDTO>builder()
                .content(content)
                .pageNumber(guestPage.getNumber())
                .pageSize(guestPage.getSize())
                .totalElements(guestPage.getTotalElements())
                .totalPages(guestPage.getTotalPages())
                .first(guestPage.isFirst())
                .last(guestPage.isLast())
                .empty(guestPage.isEmpty())
                .build();
    }
}
