package ar.com.unla.api.models.database;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ApiModel
public class PeriodoInscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "fechaDesde", required = true, example = "2020-02-02", position = 1)
    private LocalDate fechaDesde;

    @Column(nullable = false)
    @ApiModelProperty(notes = "fechaHasta", required = true, example = "2020-02-02", position = 2)
    private LocalDate fechaHasta;

    @Column(nullable = false)
    @ApiModelProperty(notes = "fechaLimiteNota", required = true, example = "2020-02-02",
            position = 3)
    private LocalDate fechaLimiteNota;

    public PeriodoInscripcion() {
    }

    public PeriodoInscripcion(LocalDate fechaDesde, LocalDate fechaHasta,
            LocalDate fechaLimiteNota) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.fechaLimiteNota = fechaLimiteNota;
    }
}
