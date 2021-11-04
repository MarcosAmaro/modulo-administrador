package ar.com.unla.api.controllers;

import ar.com.unla.api.dtos.request.MateriaDTO;
import ar.com.unla.api.models.database.Materia;
import ar.com.unla.api.models.response.ApplicationResponse;
import ar.com.unla.api.models.response.ErrorResponse;
import ar.com.unla.api.models.swagger.materia.SwaggerMateriaFindAllOk;
import ar.com.unla.api.models.swagger.materia.SwaggerMateriaOk;
import ar.com.unla.api.services.MateriaService;
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

@Api(tags = "Materia controller", description = "CRUD materia")
@Validated
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @PostMapping
    @ApiOperation(value = "Se encarga de crear y persistir una materia")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Materia creada", response =
                            SwaggerMateriaOk.class),
                    @ApiResponse(code = 400, message = "Request incorrecta al crear una Materia",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message = "Error interno al crear una Materia",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse<Materia> create(@Valid @RequestBody MateriaDTO materiaDTO) {
        return new ApplicationResponse<>(materiaService.create(materiaDTO), null);
    }

    @GetMapping
    @ApiOperation(value = "Se encarga de buscar una materia por su id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Materia encontrada", response =
                            SwaggerMateriaOk.class),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al buscar una materia por su id",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al buscar una materia por su id",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<Materia> findById(
            @RequestParam(name = "idMateria")
            @NotNull(message = "El parámetro idMateria no esta informado.")
            @ApiParam(required = true) Long id) {
        return new ApplicationResponse<>(materiaService.findById(id), null);
    }

    @GetMapping(path = "/all")
    @ApiOperation(value = "Se encarga de buscar una lista de materias")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Materias encontradas", response =
                            SwaggerMateriaFindAllOk.class),
                    @ApiResponse(code = 400, message = "Request incorrecta al buscar una lista de"
                            + " materias", response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al buscar una lista de materias",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<List<Materia>> findAll() {
        return new ApplicationResponse<>(materiaService.findAll(), null);
    }

    @PutMapping
    @ApiOperation(value = "Se encarga de actualizar una materia")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Materia actualizada", response =
                            SwaggerMateriaOk.class),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al actualizar una materia",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al actualizar una materia",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<Materia> updateSubjects(
            @RequestParam(name = "idMateria")
            @NotNull(message = "El parámetro idMateria no esta informado.")
            @ApiParam(required = true) Long id,
            @Valid @RequestBody MateriaDTO materiaDTO) {
        return new ApplicationResponse<>(
                materiaService.updateSubjects(id, materiaDTO), null);
    }

    @DeleteMapping
    @ApiOperation(value = "Se encarga eliminar una materia por su id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 204, message = "Materia eliminada"),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al eliminar una materia por su id",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error al intentar eliminar una materia por su id",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestParam(name = "idMateria")
            @NotNull(message = "El parámetro idMateria no esta informado.")
            @ApiParam(required = true) Long id) {
        materiaService.delete(id);
    }

    @GetMapping("/cuatrimestres-pdf")
    @ApiOperation(value = "Se encarga de generar un pdf con el calendario academico (Materias, "
            + "cuatrimestre y horarios)")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        materiaService.exportToPDF(response);
    }
}
