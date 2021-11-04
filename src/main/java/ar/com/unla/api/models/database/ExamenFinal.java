package ar.com.unla.api.models.database;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ApiModel
public class ExamenFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "fecha", required = true, position = 1)
    private LocalDate fecha;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "idMateria")
    @ApiModelProperty(notes = "materia", required = true, position = 2)
    private Materia materia;

    @OneToOne(optional = false, targetEntity = PeriodoInscripcion.class, cascade = {
            CascadeType.ALL})
    @JoinColumn(name = "idPeriodoInscripcion")
    @ApiModelProperty(notes = "periodoInscripcion", required = true, position = 3)
    private PeriodoInscripcion periodoInscripcion;

    public ExamenFinal() {
    }

    public ExamenFinal(LocalDate fecha, Materia materia,
            PeriodoInscripcion periodoInscripcion) {
        this.fecha = fecha;
        this.materia = materia;
        this.periodoInscripcion = periodoInscripcion;
    }
}
