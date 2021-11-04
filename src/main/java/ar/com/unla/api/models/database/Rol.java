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
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(notes = "descripcion", required = true, example = "administrador",
            position = 1)
    private String descripcion;

    public Rol() {
    }

    public Rol(String descripcion) {
        this.descripcion = descripcion;
    }
}
