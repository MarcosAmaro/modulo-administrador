package ar.com.unla.api.controllers;

import ar.com.unla.api.dtos.request.ExamenFinalDTO;
import ar.com.unla.api.models.database.ExamenFinal;
import ar.com.unla.api.models.response.ApplicationResponse;
import ar.com.unla.api.models.response.ErrorResponse;
import ar.com.unla.api.models.swagger.examenfinal.SwaggerExamenFinalFindAllOk;
import ar.com.unla.api.models.swagger.examenfinal.SwaggerExamenFinalOk;
import ar.com.unla.api.models.swagger.materia.SwaggerMateriaOk;
import ar.com.unla.api.services.ExamenFinalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Examen final controller", description = "CRUD examen final")
@Validated
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/finales")
public class ExamenFinalController {

    @Autowired
    private ExamenFinalService examenFinalService;

    @PostMapping
    @ApiOperation(value = "Se encarga de crear y persistir un examen final")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Examen final creado", response =
                            SwaggerExamenFinalOk.class),
                    @ApiResponse(code = 400, message = "Request incorrecta al crear un examen "
                            + "final", response = ErrorResponse.class),
                    @ApiResponse(code = 500, message = "Error interno al crear un examen final",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse<ExamenFinal> create(
            @Valid @RequestBody ExamenFinalDTO examenFinalDTO) {
        return new ApplicationResponse<>(examenFinalService.create(examenFinalDTO), null);
    }

    @GetMapping
    @ApiOperation(value = "Se encarga de buscar un examen final por su id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Examen final encontrado", response =
                            SwaggerExamenFinalOk.class),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al buscar un examen final por su id",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al buscar un examen final por su id",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<ExamenFinal> findById(
            @RequestParam(name = "idExamenFinal")
            @NotNull(message = "El parámetro idExamenFinal no esta informado.")
            @ApiParam(required = true) Long id) {
        return new ApplicationResponse<>(examenFinalService.findById(id), null);
    }

    @GetMapping(path = "/all")
    @ApiOperation(value = "Se encarga de buscar una lista de examenes finales")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Examenes finales encontrados", response =
                            SwaggerExamenFinalFindAllOk.class),
                    @ApiResponse(code = 400, message = "Request incorrecta al buscar una lista de"
                            + " examenes finales", response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al buscar una lista de examenes finales",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<List<ExamenFinal>> findAll() {
        return new ApplicationResponse<>(examenFinalService.findAll(), null);
    }

    @GetMapping("/finales-pdf")
    @ApiOperation(value = "Se encarga de generar un pdf con la planilla de los finales")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        examenFinalService.exportToPDF(response);
    }

    @PutMapping
    @ApiOperation(value = "Se encarga de actualizar un examen final")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Examen final actualizado", response =
                            SwaggerMateriaOk.class),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al actualizar un examen final",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al actualizar un examen final",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<ExamenFinal> updateFinalExam(
            @RequestParam(name = "idExamenFinal")
            @NotNull(message = "El parámetro idExamenFinal no esta informado.")
            @ApiParam(required = true) Long id,
            @Valid @RequestBody ExamenFinalDTO examenFinalDTO) {
        return new ApplicationResponse<>(
                examenFinalService.updateFinalExam(id, examenFinalDTO), null);
    }

    @DeleteMapping
    @ApiOperation(value = "Se encarga eliminar una examen final por su id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 204, message = "Examen final eliminado"),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al eliminar un examen final por su id",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error al intentar eliminar un examen final por su id",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestParam(name = "idExamenFinal")
            @NotNull(message = "El parámetro idExamenFinal no esta informado.")
            @ApiParam(required = true) Long id) {
        examenFinalService.delete(id);
    }
}
