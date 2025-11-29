package com.wedding.rsvp.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestRequestDTO {
    
    @NotNull(message = "Debe indicar si asistirá o no")
    private Boolean attending;
    
    @NotNull(message = "El número de adultos es obligatorio")
    @Min(value = 0, message = "El número de adultos no puede ser negativo")
    @Max(value = 20, message = "El número de adultos no puede exceder 20")
    private Integer numberOfAdults;
    
    @NotNull(message = "El número de niños es obligatorio")
    @Min(value = 0, message = "El número de niños no puede ser negativo")
    @Max(value = 20, message = "El número de niños no puede exceder 20")
    private Integer numberOfChildren;
    
    @Valid
    @NotNull(message = "La lista de adultos es obligatoria")
    @Size(min = 1, message = "Debe proporcionar al menos un adulto")
    private List<AdultDTO> adults;
    
    @Email(message = "El email de contacto debe ser válido")
    private String contactEmail;
    
    @Pattern(regexp = "^[0-9]{10}$|^$", message = "El teléfono debe tener 10 dígitos")
    private String contactPhone;
    
    @Size(max = 500, message = "Las notas no pueden exceder 500 caracteres")
    private String notes;
}
