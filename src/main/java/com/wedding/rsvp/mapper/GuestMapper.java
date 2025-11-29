package com.wedding.rsvp.mapper;

import com.wedding.rsvp.model.dto.AdultDTO;
import com.wedding.rsvp.model.dto.GuestRequestDTO;
import com.wedding.rsvp.model.dto.GuestResponseDTO;
import com.wedding.rsvp.model.entity.Adult;
import com.wedding.rsvp.model.entity.Guest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GuestMapper {
    
    /**
     * Convierte GuestRequestDTO a Guest Entity
     */
    public Guest toEntity(GuestRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Guest guest = Guest.builder()
                .attending(dto.getAttending())
                .numberOfAdults(dto.getNumberOfAdults())
                .numberOfChildren(dto.getNumberOfChildren())
                .contactEmail(dto.getContactEmail())
                .contactPhone(dto.getContactPhone())
                .notes(dto.getNotes())
                .build();
        
        // Mapear adultos
        if (dto.getAdults() != null) {
            dto.getAdults().forEach(adultDTO -> {
                Adult adult = toAdultEntity(adultDTO);
                guest.addAdult(adult);
            });
        }
        
        return guest;
    }
    
    /**
     * Convierte Guest Entity a GuestResponseDTO
     */
    public GuestResponseDTO toResponseDTO(Guest entity) {
        if (entity == null) {
            return null;
        }
        
        return GuestResponseDTO.builder()
                .id(entity.getId())
                .attending(entity.getAttending())
                .numberOfAdults(entity.getNumberOfAdults())
                .numberOfChildren(entity.getNumberOfChildren())
                .contactEmail(entity.getContactEmail())
                .contactPhone(entity.getContactPhone())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .adults(toAdultDTOList(entity.getAdults()))
                .build();
    }
    
    /**
     * Convierte AdultDTO a Adult Entity
     */
    private Adult toAdultEntity(AdultDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Adult.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .allergies(dto.getAllergies())
                .menu(dto.getMenu())
                .adultOrder(dto.getAdultOrder())
                .build();
    }
    
    /**
     * Convierte Adult Entity a AdultDTO
     */
    private AdultDTO toAdultDTO(Adult entity) {
        if (entity == null) {
            return null;
        }
        
        return AdultDTO.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .allergies(entity.getAllergies())
                .menu(entity.getMenu())
                .adultOrder(entity.getAdultOrder())
                .build();
    }
    
    /**
     * Convierte lista de Adult Entity a lista de AdultDTO
     */
    private List<AdultDTO> toAdultDTOList(List<Adult> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toAdultDTO)
                .collect(Collectors.toList());
    }
}
