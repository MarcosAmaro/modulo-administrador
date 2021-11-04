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
public class UsuarioExamenFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "idExamenFinal")
    @ApiModelProperty(notes = "examenFinal", required = true, position = 1)
    private ExamenFinal examenFinal;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "idUsuario")
    @ApiModelProperty(notes = "usuario", required = true, position = 2)
    private Usuario usuario;

    @Column(nullable = false)
    @ApiModelProperty(notes = "recordatorio", required = true, position = 3)
    private Boolean recordatorio;

    @Column(nullable = false)
    @ApiModelProperty(notes = "calificacion", required = true, position = 4)
    private float calificacion;

    public UsuarioExamenFinal() {
    }

    public UsuarioExamenFinal(ExamenFinal examenFinal, Usuario usuario, Boolean recordatorio,
            float calificacion) {
        this.examenFinal = examenFinal;
        this.usuario = usuario;
        this.recordatorio = recordatorio;
        this.calificacion = calificacion;
    }
}
