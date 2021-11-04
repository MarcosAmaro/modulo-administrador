package ar.com.unla.api.models.database;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "descripcion", required = true, example = "mañana", position = 1)
    private String descripcion;

    @Column(nullable = false)
    @ApiModelProperty(notes = "horaDesde", required = true, example = "16:00", position = 2)
    private String horaDesde;

    @Column(nullable = false)
    @ApiModelProperty(notes = "horaHasta", required = true, example = "21:00", position = 3)
    private String horaHasta;

    public Turno() {
    }

    public Turno(String descripcion, String horaDesde, String horaHasta) {
        this.descripcion = descripcion;
        this.horaDesde = horaDesde;
        this.horaHasta = horaHasta;
    }
}
