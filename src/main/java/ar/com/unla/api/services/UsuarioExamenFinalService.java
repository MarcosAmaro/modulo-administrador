package ar.com.unla.api.services;

import ar.com.unla.api.constants.CommonsErrorConstants;
import ar.com.unla.api.dtos.request.UsuarioExamenFinalDTO;
import ar.com.unla.api.dtos.response.AlumnoFinalDTO;
import ar.com.unla.api.dtos.response.AlumnoFinalFlagDTO;
import ar.com.unla.api.dtos.response.AlumnosFinalDTO;
import ar.com.unla.api.dtos.response.FinalesInscriptosDTO;
import ar.com.unla.api.exceptions.NotFoundApiException;
import ar.com.unla.api.exceptions.TransactionBlockedException;
import ar.com.unla.api.models.database.ExamenFinal;
import ar.com.unla.api.models.database.Materia;
import ar.com.unla.api.models.database.Usuario;
import ar.com.unla.api.models.database.UsuarioExamenFinal;
import ar.com.unla.api.models.enums.RolesEnum;
import ar.com.unla.api.repositories.UsuarioExamenFinalRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioExamenFinalService {

    @Autowired
    private UsuarioExamenFinalRepository usuarioExamenFinalRepository;

    @Autowired
    private ExamenFinalService examenFinalService;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MailService mailService;

    public UsuarioExamenFinal create(UsuarioExamenFinalDTO usuarioExamenFinalDTO) {

        ExamenFinal examenFinal =
                examenFinalService.findById(usuarioExamenFinalDTO.getIdExamenFinal());

        Usuario usuario = usuarioService.findById(usuarioExamenFinalDTO.getIdUsuario());

        UsuarioExamenFinal usuarioExamenFinal = new UsuarioExamenFinal(examenFinal, usuario,
                usuarioExamenFinalDTO.getRecordatorio(), usuarioExamenFinalDTO.getCalificacion());

        return usuarioExamenFinalRepository.save(usuarioExamenFinal);
    }

    public UsuarioExamenFinal findById(Long id) {
        return usuarioExamenFinalRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException(
                        "Id UsuarioExamenFinal incorrecto. No se encontro el UsuarioExamenFinal "
                                + "indicado."));
    }

    public UsuarioExamenFinal updateRemainder(Long id, Boolean recordatorio) {
        UsuarioExamenFinal usuarioExamenFinal = findById(id);
        usuarioExamenFinal.setRecordatorio(recordatorio);

        if (recordatorio) {
            mailService.sendMail(usuarioExamenFinal.getUsuario().getEmail(),
                    "Notificación Final UNLa" + "\n\n",
                    "Materia: " + usuarioExamenFinal.getExamenFinal().getMateria().getNombre()
                            + "\n" +
                            "Fecha: " + usuarioExamenFinal.getExamenFinal().getFecha().toString()
                            + "\n" +
                            "Horario: "
                            + usuarioExamenFinal.getExamenFinal().getMateria().getTurno()
                            .getHoraDesde() + " - " + usuarioExamenFinal.getExamenFinal()
                            .getMateria().getTurno().getHoraHasta());
        }

        return usuarioExamenFinalRepository.save(usuarioExamenFinal);
    }

    public UsuarioExamenFinal updateQualification(Long id, float calificacion) {
        UsuarioExamenFinal usuarioExamenFinal = findById(id);
        usuarioExamenFinal.setCalificacion(calificacion);
        return usuarioExamenFinalRepository.save(usuarioExamenFinal);
    }

    public List<AlumnosFinalDTO> findUsersByFinalExam(Long idMateria) {
        materiaService.findById(idMateria);
        List<UsuarioExamenFinal> usuariosFinal =
                usuarioExamenFinalRepository.findStudentsByFinalExam(idMateria);

        List<AlumnosFinalDTO> alumnos = new ArrayList<>();

        if (usuariosFinal != null && !usuariosFinal.isEmpty()) {
            AlumnosFinalDTO alumnosFinalDTO = new AlumnosFinalDTO();
            alumnosFinalDTO.setExamenFinal(usuariosFinal.get(0).getExamenFinal());
            alumnosFinalDTO.setAlumnos(new ArrayList<>());

            for (UsuarioExamenFinal uex : usuariosFinal) {
                AlumnoFinalDTO alumno =
                        new AlumnoFinalDTO(uex.getUsuario(), uex.getCalificacion(), uex.getId());
                alumnosFinalDTO.getAlumnos().add(alumno);
            }
            alumnos.add(alumnosFinalDTO);
        }

        return alumnos;
    }

    public List<AlumnoFinalFlagDTO> findUsersByFinalExamWithFlag(Long idMateria) {
        Materia materia = materiaService.findById(idMateria);

        List<Usuario> alumnos = usuarioService.findAllStudents();

        ExamenFinal examenFinal =
                examenFinalService.findBySubjects(idMateria, materia.getTurno().getId());

        List<AlumnoFinalFlagDTO> alumnosFinal = new ArrayList<>();

        List<UsuarioExamenFinal> usuariosFinal =
                usuarioExamenFinalRepository.findStudentsByFinalExam(idMateria);

        if (usuariosFinal != null && !usuariosFinal.isEmpty()) {
            for (UsuarioExamenFinal uex : usuariosFinal) {
                AlumnoFinalFlagDTO alumno =
                        new AlumnoFinalFlagDTO(uex.getUsuario(), true, uex.getCalificacion(),
                                uex.getId(), examenFinal.getId());
                alumnosFinal.add(alumno);
            }
            boolean encontrado = false;
            if (alumnos != null && !alumnos.isEmpty()) {
                for (Usuario alumno : alumnos) {
                    for (UsuarioExamenFinal uex : usuariosFinal) {
                        if (alumno.getId().equals(uex.getUsuario().getId())) {
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        AlumnoFinalFlagDTO alumnoNoInscripto =
                                new AlumnoFinalFlagDTO(alumno, false, 0,
                                        null, examenFinal.getId());
                        alumnosFinal.add(alumnoNoInscripto);
                    }
                    encontrado = false;
                }
            }

        } else {
            if (alumnos != null && !alumnos.isEmpty()) {
                for (Usuario alumno : alumnos) {
                    AlumnoFinalFlagDTO alumnoNoInscripto =
                            new AlumnoFinalFlagDTO(alumno, false, 0,
                                    null, examenFinal.getId());
                    alumnosFinal.add(alumnoNoInscripto);
                }
            }
        }

        return alumnosFinal;
    }

    public UsuarioExamenFinal findUsuarioExamenFinal(long idMateria, long idUsuario, String turno) {
        if (usuarioService.findById(idUsuario).getRol().getDescripcion().equals("docente")) {
            return usuarioExamenFinalRepository.findUserFinalExam(idMateria, idUsuario, turno)
                    .orElse(new UsuarioExamenFinal());
        } else {
            return usuarioExamenFinalRepository.findUserFinalExam(idMateria, idUsuario, turno)
                    .orElseThrow(() -> new NotFoundApiException(
                            "No se encontro el examen final con el usuario indicado."));
        }
    }

    public List<UsuarioExamenFinal> findFinalExamsByUser(Long idUsuario) {
        usuarioService.findById(idUsuario);
        return usuarioExamenFinalRepository.findFinalExamsByUser(idUsuario);
    }

    public List<FinalesInscriptosDTO> findSubjectsAccordingRole(Long idUsuario) {
        Usuario usuario = usuarioService.findById(idUsuario);

        try {
            if (RolesEnum.ADMINISTRADOR.name()
                    .equalsIgnoreCase(usuario.getRol().getDescripcion())) {

                return findFinalsAdmin();

            } else if (RolesEnum.DOCENTE.name()
                    .equalsIgnoreCase(usuario.getRol().getDescripcion())) {

                return findFinalsTeacher(idUsuario);

            } else if (RolesEnum.ALUMNO.name()
                    .equalsIgnoreCase(usuario.getRol().getDescripcion())) {

                return findFinalsWithInscriptionStudents(idUsuario);

            } else {
                throw new NotFoundApiException(CommonsErrorConstants.ROLE_NOT_FOUND_ERROR_MESSAGE);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    String.format(CommonsErrorConstants.LIST_INTERNAL_ERROR_MESSAGE,
                            "examenes finales"));

        }
    }

    private List<FinalesInscriptosDTO> findFinalsAdmin() {
        List<ExamenFinal> finals = examenFinalService.findAll();
        List<FinalesInscriptosDTO> finalsAdmin = new ArrayList<>();

        if (!finals.isEmpty()) {
            for (ExamenFinal examen : finals) {

                FinalesInscriptosDTO inscriptedFinal
                        = new FinalesInscriptosDTO(examen.getId(), examen.getFecha(),
                        examen.getMateria(), examen.getPeriodoInscripcion()
                        , null, null, null);

                finalsAdmin.add(inscriptedFinal);
            }
        }

        return finalsAdmin;
    }

    private List<FinalesInscriptosDTO> findFinalsTeacher(Long idUsuario) {
        List<UsuarioExamenFinal> finalsByUser = findFinalExamsByUser(idUsuario);
        List<FinalesInscriptosDTO> finalsTeacher = new ArrayList<>();

        if (!finalsByUser.isEmpty()) {
            for (UsuarioExamenFinal usm : finalsByUser) {

                FinalesInscriptosDTO inscriptedFinal
                        = new FinalesInscriptosDTO(
                        usm.getExamenFinal().getId(), usm.getExamenFinal().getFecha(),
                        usm.getExamenFinal().getMateria(),
                        usm.getExamenFinal().getPeriodoInscripcion()
                        , true, usm.getRecordatorio(), usm.getExamenFinal().getId());

                finalsTeacher.add(inscriptedFinal);
            }
        }

        return finalsTeacher;
    }

    private List<FinalesInscriptosDTO> findFinalsWithInscriptionStudents(Long idUsuario) {
        List<UsuarioExamenFinal> finalsByUser = findFinalExamsByUser(idUsuario);
        List<ExamenFinal> allFinals = examenFinalService.findAll();

        List<FinalesInscriptosDTO> finalsWithInscriptionFlag = new ArrayList<>();

        if (allFinals != null && !allFinals.isEmpty()) {
            for (ExamenFinal examenFinal : allFinals) {

                FinalesInscriptosDTO inscriptedFinal
                        = new FinalesInscriptosDTO(
                        examenFinal.getId(), examenFinal.getFecha(),
                        examenFinal.getMateria(), examenFinal.getPeriodoInscripcion()
                        , false, false, 0L);
                if (finalsByUser != null && !finalsByUser.isEmpty()) {
                    for (UsuarioExamenFinal usuarioFinal : finalsByUser) {
                        if (usuarioFinal.getExamenFinal().getId().equals(examenFinal.getId())) {
                            inscriptedFinal.setInscripto(true);
                            inscriptedFinal.setRecordatorio(usuarioFinal.getRecordatorio());
                            inscriptedFinal.setIdInscripcion(usuarioFinal.getId());
                            break;
                        }
                    }
                }
                finalsWithInscriptionFlag.add(inscriptedFinal);
            }

            finalsWithInscriptionFlag = finalsWithInscriptionFlag.stream()
                    .filter(finales ->
                            (finales.getInscripto()) || (!finales.getInscripto() &&
                                    ((finales.getPeriodoInscripcion().getFechaHasta()
                                            .isAfter(LocalDate.now()))
                                            || (finales.getPeriodoInscripcion().getFechaHasta()
                                            .equals(LocalDate.now())))

                                    && ((finales.getPeriodoInscripcion().getFechaDesde()
                                    .isBefore(LocalDate.now()))
                                    || (finales.getPeriodoInscripcion().getFechaDesde()
                                    .equals(LocalDate.now())))
                            )).collect(Collectors.toList());
        }

        return finalsWithInscriptionFlag;
    }

    public void delete(Long id) {
        UsuarioExamenFinal usuarioExamenFinal = findById(id);

        if (usuarioExamenFinal.getExamenFinal().getPeriodoInscripcion().getFechaHasta()
                .isBefore(LocalDate.now())) {
            throw new TransactionBlockedException(
                    "El periodo de inscripción cerró, no es posible darse de baja.");
        } else {
            usuarioExamenFinalRepository.deleteById(id);
        }
    }

    public void deleteAdmin(Long id) {
        findById(id);
        usuarioExamenFinalRepository.deleteById(id);

    }
}
