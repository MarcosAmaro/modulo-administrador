package ar.com.unla.api.services;

import ar.com.unla.api.dtos.request.DiaSemanaDTO;
import ar.com.unla.api.exceptions.NotFoundApiException;
import ar.com.unla.api.models.database.DiaSemana;
import ar.com.unla.api.repositories.DiaSemanaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaSemanaService {

    @Autowired
    private DiaSemanaRepository diaSemanaRepository;

    @Autowired
    private MateriaService materiaService;

    public DiaSemana create(DiaSemanaDTO diaSemanaDTO) {

        DiaSemana diaSemana =
                new DiaSemana(diaSemanaDTO.getNombre().toLowerCase());

        return diaSemanaRepository.save(diaSemana);
    }

    public DiaSemana findById(Long id) {
        return diaSemanaRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException(
                        "Id día incorrecto. No se encontro el día indicado."));
    }

    public List<DiaSemana> findAll() {
        return diaSemanaRepository.findAll();
    }

    public void delete(Long id) {
        findById(id);
        diaSemanaRepository.deleteById(id);
    }
}
