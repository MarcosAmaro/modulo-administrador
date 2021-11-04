package ar.com.unla.api.models.database;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ApiModel
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UsuarioMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "idMateria")
    @ApiModelProperty(notes = "materia", required = true, position = 1)
    private Materia materia;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "idUsuario")
    @ApiModelProperty(notes = "usuario", required = true, position = 2)
    private Usuario usuario;

    @Column(nullable = false)
    @ApiModelProperty(notes = "calificacionExamen", required = true, position = 4)
    private float calificacionExamen;

    @Column(nullable = false)
    @ApiModelProperty(notes = "calificacionTps", required = true, position = 4)
    private float calificacionTps;

    public UsuarioMateria() {
    }

    public UsuarioMateria(Materia materia, Usuario usuario, float calificacionExamen,
            float calificacionTps) {
        this.materia = materia;
        this.usuario = usuario;
        this.calificacionExamen = calificacionExamen;
        this.calificacionTps = calificacionTps;
    }
}
