package com.wedding.rsvp.config;

import com.wedding.rsvp.model.entity.Adult;
import com.wedding.rsvp.model.entity.Guest;
import com.wedding.rsvp.model.enums.MenuType;
import com.wedding.rsvp.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataLoader {
    
    private final GuestRepository guestRepository;
    
    @Bean
    @Profile("!prod") // Solo ejecutar en desarrollo, no en producción
    CommandLineRunner loadData() {
        return args -> {
            log.info("Cargando datos de prueba...");
            
            // Guest 1 - Asiste con 2 adultos
            Guest guest1 = Guest.builder()
                    .attending(true)
                    .numberOfAdults(2)
                    .numberOfChildren(0)
                    .contactEmail("juan.perez@example.com")
                    .contactPhone("5551234567")
                    .notes("Mesa cerca de la pista de baile")
                    .build();
            
            Adult adult1 = Adult.builder()
                    .fullName("Juan Pérez García")
                    .allergies("Ninguna")
                    .menu(MenuType.CARNE)
                    .adultOrder(1)
                    .build();
            
            Adult adult2 = Adult.builder()
                    .fullName("María González López")
                    .allergies("Alérgica a los mariscos")
                    .menu(MenuType.VEGANO)
                    .adultOrder(2)
                    .build();
            
            guest1.addAdult(adult1);
            guest1.addAdult(adult2);
            
            // Guest 2 - Asiste con 1 adulto y 2 niños
            Guest guest2 = Guest.builder()
                    .attending(true)
                    .numberOfAdults(1)
                    .numberOfChildren(2)
                    .contactEmail("ana.martinez@example.com")
                    .contactPhone("5559876543")
                    .build();
            
            Adult adult3 = Adult.builder()
                    .fullName("Ana Martínez Rodríguez")
                    .menu(MenuType.SALMON)
                    .adultOrder(1)
                    .build();
            
            guest2.addAdult(adult3);
            
            // Guest 3 - NO asiste
            Guest guest3 = Guest.builder()
                    .attending(false)
                    .numberOfAdults(1)
                    .numberOfChildren(0)
                    .contactEmail("carlos.lopez@example.com")
                    .contactPhone("5551112233")
                    .notes("No puedo asistir por viaje de trabajo")
                    .build();
            
            Adult adult4 = Adult.builder()
                    .fullName("Carlos López Sánchez")
                    .menu(MenuType.CARNE)
                    .adultOrder(1)
                    .build();
            
            guest3.addAdult(adult4);
            
            // Guardar todos
            guestRepository.save(guest1);
            guestRepository.save(guest2);
            guestRepository.save(guest3);
            
            log.info("Datos de prueba cargados exitosamente");
            log.info("Total de invitados: {}", guestRepository.count());
        };
    }
}
