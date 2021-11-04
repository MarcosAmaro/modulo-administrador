package ar.com.unla.api.services;

import ar.com.unla.api.dtos.request.DatosContactoUsuarioDTO;
import ar.com.unla.api.dtos.request.DatosSensiblesUsuarioDTO;
import ar.com.unla.api.dtos.request.LoginUsuarioDTO;
import ar.com.unla.api.dtos.request.UpdatePassDTO;
import ar.com.unla.api.dtos.request.UsuarioDTO;
import ar.com.unla.api.exceptions.NotFoundApiException;
import ar.com.unla.api.exceptions.TransactionBlockedException;
import ar.com.unla.api.models.database.Direccion;
import ar.com.unla.api.models.database.Rol;
import ar.com.unla.api.models.database.Usuario;
import ar.com.unla.api.repositories.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private RolService rolService;

    @Autowired
    private MailService mailService;

    public Usuario create(UsuarioDTO usuarioDTO) {

        Rol rol = rolService.findById(usuarioDTO.getIdRol());

        Direccion direccion = new Direccion(usuarioDTO.getDireccion().getPais(),
                usuarioDTO.getDireccion().getProvincia(), usuarioDTO.getDireccion().getLocalidad(),
                usuarioDTO.getDireccion().getCalle());

        Usuario usuario = new Usuario(usuarioDTO.getNombre(), usuarioDTO.getApellido(),
                usuarioDTO.getTelefono(), usuarioDTO.getDni(), usuarioDTO.getEmail().toLowerCase(),
                direccion, usuarioDTO.getPassword(), usuarioDTO.getImagen(),
                usuarioDTO.getPrimerIngreso(), rol);

        mailService.sendMail(usuario.getEmail(), "Alta de usuario\n\n",
                "Usuario: " + usuario.getEmail() + "\n" +
                        "Password: " + usuario.getPassword() + "\n\n" +
                        "El password es provisorio, debera cambiarse en el primer ingreso.");

        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException(
                        "Id usuario incorrecto. No se encontro el usuario indicado."));
    }

    public Usuario findByEmailAndPass(LoginUsuarioDTO loginUsuario) {
        return usuarioRepository.findUserByPassAndEmail(loginUsuario.getEmail().toLowerCase(),
                loginUsuario.getPassword())
                .orElseThrow(() -> new NotFoundApiException("Email o password incorrecto."));
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> findAllTeachers() {
        return usuarioRepository.findTeacherUsers();
    }

    public List<Usuario> findAllStudents() {
        return usuarioRepository.findStudentUsers();
    }

    public Usuario updateSensitiveData(Long id, DatosSensiblesUsuarioDTO datosSensiblesUsuarioDTO) {
        Usuario usuario = findById(id);
        usuario.setNombre(datosSensiblesUsuarioDTO.getNombre());
        usuario.setApellido(datosSensiblesUsuarioDTO.getApellido());
        usuario.setDni(datosSensiblesUsuarioDTO.getDni());
        usuario.setRol(rolService.findById(datosSensiblesUsuarioDTO.getIdRol()));
        usuario.setPrimerIngreso(datosSensiblesUsuarioDTO.getPrimerIngreso());

        long idDireccionAnterior = usuario.getDireccion().getId();

        usuario.setTelefono(datosSensiblesUsuarioDTO.getTelefono());

        Direccion direccion = new Direccion(datosSensiblesUsuarioDTO.getDireccion().getPais(),
                datosSensiblesUsuarioDTO.getDireccion().getProvincia(),
                datosSensiblesUsuarioDTO.getDireccion().getLocalidad(),
                datosSensiblesUsuarioDTO.getDireccion().getCalle());

        usuario.setDireccion(direccion);
        usuario.setImagen(datosSensiblesUsuarioDTO.getImagen());

        usuario = usuarioRepository.save(usuario);

        direccionService.delete(idDireccionAnterior);
        return usuario;
    }

    public Usuario updateContactInformation(Long id, DatosContactoUsuarioDTO datosContacto) {
        Usuario usuario = findById(id);
        long idDireccionAnterior = usuario.getDireccion().getId();

        usuario.setTelefono(datosContacto.getTelefono());
        usuario.setEmail(datosContacto.getEmail());

        Direccion direccion = new Direccion(datosContacto.getDireccion().getPais(),
                datosContacto.getDireccion().getProvincia(),
                datosContacto.getDireccion().getLocalidad(),
                datosContacto.getDireccion().getCalle());

        usuario.setDireccion(direccion);
        usuario.setImagen(datosContacto.getImagen());

        usuario = usuarioRepository.save(usuario);

        direccionService.delete(idDireccionAnterior);

        return usuario;
    }

    public String updatePassword(UpdatePassDTO loginUsuario) {
        Usuario usuario = findById(loginUsuario.getIdUsuario());

        usuario.setPassword(loginUsuario.getPassword());
        usuario.setPrimerIngreso(false);

        usuarioRepository.save(usuario);

        return "Contraseña actualizada con éxito";
    }

    public void delete(Long id) {
        try {
            findById(id);
            usuarioRepository.deleteById(id);
        } catch (RuntimeException e) {
            if (e instanceof NotFoundApiException) {
                throw new NotFoundApiException(e.getMessage());
            }
            throw new TransactionBlockedException(
                    "No se puede eliminar el usuario porque esta relacionado a otros elementos de"
                            + " la aplicación");
        }
    }
}
