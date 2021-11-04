package ar.com.unla.api.services;

import ar.com.unla.api.dtos.request.DireccionDTO;
import ar.com.unla.api.exceptions.NotFoundApiException;
import ar.com.unla.api.models.database.Direccion;
import ar.com.unla.api.repositories.DireccionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    public Direccion create(DireccionDTO direccionDTO) {

        Direccion direccion = new Direccion(direccionDTO.getPais(), direccionDTO.getProvincia(),
                direccionDTO.getLocalidad(), direccionDTO.getCalle());

        return direccionRepository.saveAndFlush(direccion);
    }

    public Direccion findById(Long id) {
        return direccionRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException(
                        "Id dirección incorrecto. No se encontro la dirección indicada."));
    }

    public List<Direccion> findAll() {
        return direccionRepository.findAllByOrderByPaisAsc();
    }

    public void delete(Long id) {
        findById(id);
        direccionRepository.deleteById(id);
    }
}
