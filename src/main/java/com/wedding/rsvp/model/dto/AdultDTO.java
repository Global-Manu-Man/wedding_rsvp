package com.wedding.rsvp.model.dto;

import com.wedding.rsvp.model.enums.MenuType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdultDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre y apellido es obligatorio")
    @Size(min = 2, max = 200, message = "El nombre debe tener entre 2 y 200 caracteres")
    private String fullName;
    
    @Size(max = 500, message = "Las alergias no pueden exceder 500 caracteres")
    private String allergies;
    
    @NotNull(message = "El tipo de menú es obligatorio")
    private MenuType menu;
    
    private Integer adultOrder;
}
