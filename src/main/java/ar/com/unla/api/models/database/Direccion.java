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
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "pais", required = true, example = "Argentina", position = 1)
    private String pais;

    @Column(nullable = false)
    @ApiModelProperty(notes = "provincia", required = true, example = "Bs As", position = 2)
    private String provincia;

    @Column(nullable = false)
    @ApiModelProperty(notes = "localidad", required = true, example = "Remedios de Escalada",
            position = 3)
    private String localidad;

    @Column(nullable = false)
    @ApiModelProperty(notes = "calle", required = true, example = "29 de Septiembre 3901",
            position = 4)
    private String calle;

    public Direccion() {
    }

    public Direccion(String pais, String provincia, String localidad, String calle) {
        this.pais = pais;
        this.provincia = provincia;
        this.localidad = localidad;
        this.calle = calle;
    }
}
