package com.wedding.rsvp.model.enums;

import lombok.Getter;

@Getter
public enum MenuType {
    CARNE("Carne de ternera con paté de patata"),
    SALMON("Lomo de salmón con champiñones"),
    VEGANO("Tartar de remolacha con crema de aguacate y nueces (Vegano)");
    
    private final String description;
    
    MenuType(String description) {
        this.description = description;
    }
}
