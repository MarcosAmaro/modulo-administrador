package ar.com.unla.api.services;

import ar.com.unla.api.dtos.request.PeriodoInscripcionDTO;
import ar.com.unla.api.exceptions.NotFoundApiException;
import ar.com.unla.api.models.database.PeriodoInscripcion;
import ar.com.unla.api.repositories.PeriodoInscripcionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodoInscripcionService {

    @Autowired
    private PeriodoInscripcionRepository periodoInscripcionRepository;

    public PeriodoInscripcion create(PeriodoInscripcionDTO periodoInscripcionDTO) {

        PeriodoInscripcion periodoInscripcion =
                new PeriodoInscripcion(periodoInscripcionDTO.getFechaDesde(),
                        periodoInscripcionDTO.getFechaHasta(),
                        periodoInscripcionDTO.getFechaLimiteNota());

        return periodoInscripcionRepository.save(periodoInscripcion);
    }

    public PeriodoInscripcion findById(Long id) {
        return periodoInscripcionRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException(
                        "Id periodo de inscripción incorrecto. No se encontro el periodo de "
                                + "inscripción indicado."));
    }

    public List<PeriodoInscripcion> findAll() {
        return periodoInscripcionRepository.findAll();
    }

    public void delete(Long id) {
        findById(id);
        periodoInscripcionRepository.deleteById(id);
    }
}
