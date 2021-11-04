package ar.com.unla.api.models.enums;

import lombok.Getter;

@Getter
public enum TurnosEnum {
    MAÑANA("09:00", "14:00"),
    TARDE("14:00", "18:00"),
    NOCHE("18:00", "22:00");

    private final String horaDesde;

    private final String horaHasta;

    TurnosEnum(String desde, String hasta) {
        this.horaDesde = desde;
        this.horaHasta = hasta;
    }
}
