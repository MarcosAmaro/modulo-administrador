package ar.com.unla.api.controllers;

import ar.com.unla.api.dtos.request.DatosContactoUsuarioDTO;
import ar.com.unla.api.dtos.request.DatosSensiblesUsuarioDTO;
import ar.com.unla.api.dtos.request.LoginUsuarioDTO;
import ar.com.unla.api.dtos.request.UpdatePassDTO;
import ar.com.unla.api.dtos.request.UsuarioDTO;
import ar.com.unla.api.models.database.Usuario;
import ar.com.unla.api.models.response.ApplicationResponse;
import ar.com.unla.api.models.response.ErrorResponse;
import ar.com.unla.api.models.swagger.usuario.SwaggerUsuarioFindAllOk;
import ar.com.unla.api.models.swagger.usuario.SwaggerUsuarioOk;
import ar.com.unla.api.models.swagger.usuarioexamenfinal.SwaggerUsuarioFinalOk;
import ar.com.unla.api.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
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

@Api(tags = "Usuario controller", description = "CRUD usuario")
@Validated
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ApiOperation(value = "Se encarga de crear y persistir un usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Usuario creado", response =
                            SwaggerUsuarioOk.class),
                    @ApiResponse(code = 400, message = "Request incorrecta al crear un Usuario",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message = "Error interno al crear un Usuario",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse<Usuario> create(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return new ApplicationResponse<>(usuarioService.create(usuarioDTO), null);
    }

    @GetMapping
    @ApiOperation(value = "Se encarga de buscar un usuario por su id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Usuario encontrado", response =
                            SwaggerUsuarioOk.class),
                    @ApiResponse(code = 400, message = "Request incorrecta al buscar un Usuario "
                            + "por su id",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message = "Error interno al buscar un Usuario por su"
                            + " id",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<Usuario> findById(
            @RequestParam(name = "idUsuario")
            @NotNull(message = "El parámetro idUsuario no esta informado.")
            @ApiParam(required = true) Long id) {
        return new ApplicationResponse<>(usuarioService.findById(id), null);
    }

    @PostMapping(path = "/login")
    @ApiOperation(value = "Se encarga de buscar un usuario por su usuario y contraseña")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Usuario encontrado", response =
                            SwaggerUsuarioOk.class),
                    @ApiResponse(code = 400, message = "Request incorrecta al buscar un Usuario "
                            + "por su id",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message = "Error interno al buscar un Usuario por su"
                            + " id",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<Usuario> findByEmailAndPass(
            @Valid @RequestBody LoginUsuarioDTO loginUsuario) {
        return new ApplicationResponse<>(usuarioService.findByEmailAndPass(loginUsuario), null);
    }

    @GetMapping(path = "/all")
    @ApiOperation(value = "Se encarga de buscar una lista de usuarios")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Usuarios encontrados", response =
                            SwaggerUsuarioFindAllOk.class),
                    @ApiResponse(code = 400, message = "Request incorrecta al buscar una lista de"
                            + " usuarios", response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al buscar una lista de usuarios",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<List<Usuario>> findAll() {
        return new ApplicationResponse<>(usuarioService.findAll(), null);
    }

    @GetMapping(path = "/docentes")
    @ApiOperation(value = "Se encarga de buscar una lista de usuarios con rol docente")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Usuarios con rol docente encontrados",
                            response = SwaggerUsuarioFindAllOk.class),
                    @ApiResponse(code = 400, message = "Request incorrecta al buscar una lista de"
                            + " usuarios con rol docente", response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al buscar una lista de usuarios con rol docente",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<List<Usuario>> findAllTeachers() {
        return new ApplicationResponse<>(usuarioService.findAllTeachers(), null);
    }

    @PutMapping
    @ApiOperation(value = "Se encarga de actualizar todos los datos de un usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Usuario actualizado", response =
                            SwaggerUsuarioFinalOk.class),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al actualizar un usuario",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al actualizar un usuario",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<Usuario> updateSensitiveData(
            @RequestParam(name = "idUsuario")
            @NotNull(message = "El parámetro idUsuario no esta informado.")
            @ApiParam(required = true) Long id,
            @Valid @RequestBody DatosSensiblesUsuarioDTO datosSensiblesUsuario) {
        return new ApplicationResponse<>(
                usuarioService.updateSensitiveData(id, datosSensiblesUsuario),
                null);
    }

    @PutMapping(path = "/datos-contacto")
    @ApiOperation(value = "Se encarga de actualizar datos de contacto de un usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Usuario actualizado", response =
                            SwaggerUsuarioFinalOk.class),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al actualizar un usuario",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al actualizar un usuario",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<Usuario> updateContactInformation(
            @RequestParam(name = "idUsuario")
            @NotNull(message = "El parámetro idUsuario no esta informado.")
            @ApiParam(required = true) Long id,
            @Valid @RequestBody DatosContactoUsuarioDTO datosContacto) {
        return new ApplicationResponse<>(usuarioService.updateContactInformation(id, datosContacto),
                null);
    }

    @PutMapping(path = "/password")
    @ApiOperation(value = "Se encarga de actualizar datos de login de un usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Usuario actualizado", response =
                            SwaggerUsuarioFinalOk.class),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al actualizar un usuario",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al actualizar un usuario",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<String> updatePassword(
            @Valid @RequestBody UpdatePassDTO loginUsuario) {
        return new ApplicationResponse<>(
                usuarioService.updatePassword(loginUsuario), null);
    }


    @DeleteMapping
    @ApiOperation(value = "Se encarga eliminar un usuario por su id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 204, message = "Usuario eliminado"),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al eliminar un usuario por su id",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error al intentar eliminar un usuario por su id",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestParam(name = "idUsuario")
            @NotNull(message = "El parámetro idUsuario no esta informado.")
            @ApiParam(required = true) Long id) {
        usuarioService.delete(id);
    }
}
