package ar.com.unla.api.services;

import ar.com.unla.api.dtos.request.RolDTO;
import ar.com.unla.api.exceptions.NotFoundApiException;
import ar.com.unla.api.models.database.Rol;
import ar.com.unla.api.repositories.RolRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;


    public Rol create(RolDTO rolDTO) {

        Rol rol = new Rol(rolDTO.getDescripcion().toLowerCase());

        return rolRepository.save(rol);
    }

    public Rol findById(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException(
                        "Id rol incorrecto. No se encontro el rol indicado."));
    }

    public List<Rol> findAll() {
        return rolRepository.findAllByOrderByDescripcionAsc();
    }

    public void delete(Long id) {
        findById(id);
        rolRepository.deleteById(id);
    }
}
