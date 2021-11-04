package ar.com.unla.api.services;

import ar.com.unla.api.dtos.request.MateriaDTO;
import ar.com.unla.api.dtos.request.UsuarioMateriaDTO;
import ar.com.unla.api.exceptions.NotFoundApiException;
import ar.com.unla.api.exceptions.TransactionBlockedException;
import ar.com.unla.api.models.database.DiaSemana;
import ar.com.unla.api.models.database.Materia;
import ar.com.unla.api.models.database.PeriodoInscripcion;
import ar.com.unla.api.models.database.Turno;
import ar.com.unla.api.models.database.Usuario;
import ar.com.unla.api.models.database.UsuarioMateria;
import ar.com.unla.api.repositories.MateriaRepository;
import ar.com.unla.api.utils.MateriaPDFExporter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DiaSemanaService diaSemanaService;

    @Autowired
    private UsuarioMateriaService usuarioMateriaService;

    @Autowired
    private PeriodoInscripcionService periodoInscripcionService;

    public Materia create(MateriaDTO materiaDTO) {

        PeriodoInscripcion inscripcionMateria =
                new PeriodoInscripcion(materiaDTO.getPeriodoInscripcion().getFechaDesde(),
                        materiaDTO.getPeriodoInscripcion().getFechaHasta(),
                        materiaDTO.getPeriodoInscripcion().getFechaLimiteNota());

        Turno turno = turnoService.findById(materiaDTO.getIdTurno());

        Usuario profesor = usuarioService.findById(materiaDTO.getIdProfesor());

        Set<DiaSemana> diasSemana = new HashSet<>();

        if (materiaDTO.getDias() != null && !materiaDTO.getDias().isEmpty()) {
            for (Long dia : materiaDTO.getDias()) {
                diasSemana.add(diaSemanaService.findById(dia));
            }
        }

        Materia materia = materiaRepository
                .save(new Materia(materiaDTO.getNombre(), profesor, materiaDTO.getCuatrimestre(),
                        materiaDTO.getAnioCarrera(), turno, inscripcionMateria));

        //Se agrega la relación del profesor con la materia
        UsuarioMateriaDTO usuarioMateriaDTO =
                new UsuarioMateriaDTO(materia.getId(), profesor.getId(), 0f, 0f);
        usuarioMateriaService.create(usuarioMateriaDTO);

        materia.getDias().addAll(diasSemana);

        return materiaRepository.save(materia);
    }

    public Materia updateSubjects(long idMateria, MateriaDTO materiaDTO) {

        Materia materiaActual = findById(idMateria);
        Usuario profesorAnterior = materiaActual.getProfesor();

        //Si la materia posee alumnos inscriptos el nombre, el turno, el año o el cuatrimestre de
        // la materia no pueden cambiar
        if (!usuarioMateriaService.findStudentsBySubject(materiaActual.getId()).isEmpty()
                && (!materiaDTO.getNombre().equalsIgnoreCase(materiaActual.getNombre())
                || !materiaDTO.getIdTurno().equals(materiaActual.getTurno().getId())
                || !materiaDTO.getAnioCarrera().equals(materiaActual.getAnioCarrera())
                || !materiaDTO.getCuatrimestre().equals(materiaActual.getCuatrimestre())
        )) {
            throw new TransactionBlockedException(
                    "No se puede editar el nombre, el turno, el año o el cuatrimestre de la "
                            + "materia porque posee alumnos inscriptos");
        }

        materiaActual.getDias().clear();

        PeriodoInscripcion inscripcionMateria =
                new PeriodoInscripcion(materiaDTO.getPeriodoInscripcion().getFechaDesde(),
                        materiaDTO.getPeriodoInscripcion().getFechaHasta(),
                        materiaDTO.getPeriodoInscripcion().getFechaLimiteNota());

        Turno turno = turnoService.findById(materiaDTO.getIdTurno());

        Usuario profesor = usuarioService.findById(materiaDTO.getIdProfesor());

        Set<DiaSemana> diasSemana = new HashSet<>();

        if (materiaDTO.getDias() != null && !materiaDTO.getDias().isEmpty()) {
            for (Long dia : materiaDTO.getDias()) {
                diasSemana.add(diaSemanaService.findById(dia));
            }
        }

        //Si el profesor cambio se borra la relación del anterior profesor con esta materia
        if (!materiaActual.getProfesor().getId().equals(materiaDTO.getIdProfesor())) {
            UsuarioMateria usuarioMateria =
                    usuarioMateriaService.findByUserAndSubject(materiaActual.getId(),
                            materiaActual.getProfesor().getId(),
                            materiaActual.getTurno().getDescripcion().toLowerCase());
            if (usuarioMateria.getId() != null) {
                usuarioMateriaService.delete(usuarioMateria.getId());
            }
        } else {
            UsuarioMateria usuarioMateria =
                    usuarioMateriaService.findByUserAndSubject(materiaActual.getId(),
                            materiaActual.getProfesor().getId(),
                            materiaActual.getTurno().getDescripcion().toLowerCase());
            if (usuarioMateria.getId() == null) {
                usuarioMateriaService
                        .create(new UsuarioMateriaDTO(materiaActual.getId(),
                                profesorAnterior.getId(), 0f,
                                0f));
            }
        }

        materiaActual.setNombre(materiaDTO.getNombre());
        materiaActual.setAnioCarrera(materiaDTO.getAnioCarrera());
        materiaActual.setCuatrimestre(materiaDTO.getCuatrimestre());
        materiaActual.setPeriodoInscripcion(inscripcionMateria);

        if (!materiaActual.getProfesor().getId().equals(materiaDTO.getIdProfesor())) {
            materiaActual.setProfesor(profesor);
        }
        materiaActual.setTurno(turno);
        materiaActual.getDias().addAll(diasSemana);

        Materia materia = materiaRepository.save(materiaActual);

        if (!profesorAnterior.getId().equals(materiaDTO.getIdProfesor())) {
            //Se agrega la nueva relacion del nuevo profesor con esta materia
            usuarioMateriaService
                    .create(new UsuarioMateriaDTO(materia.getId(), profesor.getId(), 0f, 0f));
        }
        return materia;
    }

    public Materia findById(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException(
                        "Id de materia incorrecto. No se encontro la materia indicada."));
    }

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    public void delete(Long id) {
        try {
            Materia materia = findById(id);

            UsuarioMateria usuarioMateria = usuarioMateriaService
                    .findByUserAndSubject(materia.getId(), materia.getProfesor().getId(),
                            materia.getTurno().getDescripcion());

            if (usuarioMateria.getId() != null) {
                usuarioMateriaService.deleteAdmin(usuarioMateria.getId());
            }

            materiaRepository.deleteById(id);
        } catch (RuntimeException e) {
            if (e instanceof NotFoundApiException) {
                throw new NotFoundApiException(e.getMessage());
            }
            throw new TransactionBlockedException(
                    "No se puede eliminar la materia porque esta relacionada a otros elementos de"
                            + " la aplicación");
        }
    }

    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=CuetrimestresUNLa.pdf";

        response.setHeader(headerKey, headerValue);

        List<Materia> materiasManiana = materiaRepository.findSubjectsForPDF(1);

        List<Materia> materiasTarde = materiaRepository.findSubjectsForPDF(2);

        List<Materia> materiasNoche = materiaRepository.findSubjectsForPDF(3);

        new MateriaPDFExporter(materiasManiana, materiasTarde, materiasNoche).export(response);
    }

}
