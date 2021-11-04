package ar.com.unla.api.services;

import ar.com.unla.api.dtos.request.ExamenFinalDTO;
import ar.com.unla.api.dtos.request.UsuarioExamenFinalDTO;
import ar.com.unla.api.exceptions.NotFoundApiException;
import ar.com.unla.api.exceptions.TransactionBlockedException;
import ar.com.unla.api.models.database.ExamenFinal;
import ar.com.unla.api.models.database.Materia;
import ar.com.unla.api.models.database.PeriodoInscripcion;
import ar.com.unla.api.models.database.UsuarioExamenFinal;
import ar.com.unla.api.repositories.ExamenFinalRepository;
import ar.com.unla.api.utils.FinalesPDFExporter;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamenFinalService {

    @Autowired
    private ExamenFinalRepository examenFinalRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private UsuarioExamenFinalService usuarioExamenFinalService;

    public ExamenFinal create(ExamenFinalDTO examenFinalDTO) {

        PeriodoInscripcion inscripcionFinal =
                new PeriodoInscripcion(examenFinalDTO.getPeriodoInscripcion().getFechaDesde(),
                        examenFinalDTO.getPeriodoInscripcion().getFechaHasta(),
                        examenFinalDTO.getPeriodoInscripcion().getFechaLimiteNota());

        Materia materia = materiaService.findById(examenFinalDTO.getIdMateria());

        ExamenFinal examenFinal = examenFinalRepository
                .save(new ExamenFinal(examenFinalDTO.getFecha(), materia, inscripcionFinal));

        UsuarioExamenFinalDTO usuarioExamenFinalDTO =
                new UsuarioExamenFinalDTO(examenFinal.getId(), materia.getProfesor().getId(), false,
                        0f);
        usuarioExamenFinalService.create(usuarioExamenFinalDTO);

        return examenFinal;
    }

    public ExamenFinal findById(Long id) {
        return examenFinalRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException("Id examen final incorrecto. No se "
                        + "encontro el examen final indicado."));
    }

    public ExamenFinal findBySubjects(Long idMateria, Long idTurno) {
        return examenFinalRepository.findBySubject(idMateria, idTurno)
                .orElseThrow(() -> new NotFoundApiException(
                        "La materia indicada no tiene un examen final creado"));
    }

    public List<ExamenFinal> findAll() {
        return examenFinalRepository.findAll();
    }


    public ExamenFinal updateFinalExam(long idFinal, ExamenFinalDTO examenFinalDTO) {

        ExamenFinal examenFinalActual = findById(idFinal);
        Long idMateriaAnterior = examenFinalActual.getMateria().getId();

        //Si el final posee alumnos inscriptos la meteria y las fechas de inscripcion no pueden
        // cambiar
        if (!usuarioExamenFinalService.findUsersByFinalExam(examenFinalActual.getMateria().getId())
                .isEmpty()
                && (!examenFinalDTO.getIdMateria().equals(examenFinalActual.getMateria().getId())
                || !examenFinalDTO.getPeriodoInscripcion().getFechaDesde()
                .equals(examenFinalActual.getPeriodoInscripcion().getFechaDesde())
                || !examenFinalDTO.getPeriodoInscripcion().getFechaHasta()
                .equals(examenFinalActual.getPeriodoInscripcion().getFechaHasta())
                || !examenFinalDTO.getFecha().equals(examenFinalActual.getFecha()))
        ) {
            throw new TransactionBlockedException(
                    "No se puede editar la materia, las fechas de inscripción o la fecha del "
                            + "final porque posee alumnos inscriptos");
        }

        PeriodoInscripcion inscripcionFinal =
                new PeriodoInscripcion(examenFinalDTO.getPeriodoInscripcion().getFechaDesde(),
                        examenFinalDTO.getPeriodoInscripcion().getFechaHasta(),
                        examenFinalDTO.getPeriodoInscripcion().getFechaLimiteNota());

        examenFinalActual.setFecha(examenFinalDTO.getFecha());
        examenFinalActual.setPeriodoInscripcion(inscripcionFinal);

        Materia materiaNueva = materiaService.findById(examenFinalDTO.getIdMateria());

        //Si la materia cambio se borra la relación del profesor anterior con este final
        if (!examenFinalActual.getMateria().getId().equals(materiaNueva.getId())) {
            UsuarioExamenFinal usuarioExamenFinal = usuarioExamenFinalService
                    .findUsuarioExamenFinal(examenFinalActual.getMateria().getId(),
                            examenFinalActual.getMateria().getProfesor().getId(),
                            examenFinalActual.getMateria().getTurno().getDescripcion());

            if (usuarioExamenFinal.getId() != null) {
                usuarioExamenFinalService
                        .delete(usuarioExamenFinal.getId());
            }
        } else {
            UsuarioExamenFinal usuarioExamenFinal = usuarioExamenFinalService
                    .findUsuarioExamenFinal(examenFinalActual.getMateria().getId(),
                            examenFinalActual.getMateria().getProfesor().getId(),
                            examenFinalActual.getMateria().getTurno().getDescripcion());
            if (usuarioExamenFinal.getId() == null) {
                usuarioExamenFinalService
                        .create(new UsuarioExamenFinalDTO(examenFinalActual.getId(),
                                examenFinalActual.getMateria().getProfesor().getId(), false, 0f));
            }
        }

        if (!examenFinalActual.getMateria().getId().equals(examenFinalDTO.getIdMateria())) {
            examenFinalActual.setMateria(materiaService.findById(examenFinalDTO.getIdMateria()));
        }

        ExamenFinal examenFinalNuevo = examenFinalRepository.save(examenFinalActual);

        //Se crea la nueva relación del profesor anterior con este final
        if (!idMateriaAnterior.equals(materiaNueva.getId())) {
            usuarioExamenFinalService.create(new UsuarioExamenFinalDTO(examenFinalNuevo.getId(),
                    examenFinalNuevo.getMateria().getProfesor().getId(), false, 0f));
        }
        return examenFinalNuevo;
    }

    public void delete(Long id) {
        try {
            ExamenFinal examenFinal = findById(id);

            UsuarioExamenFinal usuarioExamenFinal = usuarioExamenFinalService
                    .findUsuarioExamenFinal(examenFinal.getMateria().getId(),
                            examenFinal.getMateria().getProfesor().getId(),
                            examenFinal.getMateria().getTurno().getDescripcion());

            if (usuarioExamenFinal.getId() != null) {
                usuarioExamenFinalService.deleteAdmin(usuarioExamenFinal.getId());
            }
            examenFinalRepository.deleteById(id);

        } catch (RuntimeException e) {
            if (e instanceof NotFoundApiException) {
                throw new NotFoundApiException(e.getMessage());
            }
            throw new TransactionBlockedException(
                    "No se puede eliminar el examen final porque esta relacionado a otros "
                            + "elementos de la aplicación");
        }
    }

    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=FinalesUNLa.pdf";

        response.setHeader(headerKey, headerValue);

        List<ExamenFinal> finalesManiana = examenFinalRepository.findFinalsForPDF(1);

        List<ExamenFinal> finalesTarde = examenFinalRepository.findFinalsForPDF(2);

        List<ExamenFinal> finalesNoche = examenFinalRepository.findFinalsForPDF(3);

        new FinalesPDFExporter(finalesManiana, finalesTarde, finalesNoche).export(response);
    }
}
