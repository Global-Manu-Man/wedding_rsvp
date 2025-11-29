package com.wedding.rsvp.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestResponseDTO {
    
    private Long id;
    private Boolean attending;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private List<AdultDTO> adults;
    private String contactEmail;
    private String contactPhone;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
