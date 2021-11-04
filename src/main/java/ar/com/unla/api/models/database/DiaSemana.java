package ar.com.unla.api.models.database;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ApiModel
@EqualsAndHashCode
public class DiaSemana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Exclude
    @ApiModelProperty(notes = "nombre", required = true, example = "miercoles", position = 4)
    private String nombre;

    public DiaSemana() {
    }

    public DiaSemana(String nombre) {
        this.nombre = nombre;
    }
}
