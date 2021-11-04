package ar.com.unla.api.dtos.response;

import ar.com.unla.api.models.database.Materia;
import ar.com.unla.api.models.database.PeriodoInscripcion;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalesInscriptosDTO {

    private Long id;

    @ApiModelProperty(notes = "fecha", position = 1)
    private LocalDate fecha;

    @ApiModelProperty(notes = "materia", position = 2)
    private Materia materia;

    @ApiModelProperty(notes = "periodoInscripcion", position = 3)
    private PeriodoInscripcion periodoInscripcion;

    @ApiModelProperty(notes = "inscripto", position = 4)
    private Boolean inscripto;

    @ApiModelProperty(notes = "recordatorio", position = 5)
    private Boolean recordatorio;

    @ApiModelProperty(notes = "idInscripcion", position = 9)
    private Long idInscripcion;
}
