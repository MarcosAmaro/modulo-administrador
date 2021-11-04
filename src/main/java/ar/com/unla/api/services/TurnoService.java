package ar.com.unla.api.services;

import ar.com.unla.api.dtos.request.TurnoDTO;
import ar.com.unla.api.exceptions.NotFoundApiException;
import ar.com.unla.api.models.database.Turno;
import ar.com.unla.api.models.enums.TurnosEnum;
import ar.com.unla.api.repositories.TurnoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;


    public Turno create(TurnoDTO turnoDTO) {

        String horaDesde = null;
        String horaHasta = null;

        if (TurnosEnum.MAÑANA.toString().equalsIgnoreCase(turnoDTO.getDescripcion())) {
            horaDesde = TurnosEnum.MAÑANA.getHoraDesde();
            horaHasta = TurnosEnum.MAÑANA.getHoraHasta();
        } else if (TurnosEnum.TARDE.toString().equalsIgnoreCase(turnoDTO.getDescripcion())) {
            horaDesde = TurnosEnum.TARDE.getHoraDesde();
            horaHasta = TurnosEnum.TARDE.getHoraHasta();
        } else if (TurnosEnum.NOCHE.toString().equalsIgnoreCase(turnoDTO.getDescripcion())) {
            horaDesde = TurnosEnum.NOCHE.getHoraDesde();
            horaHasta = TurnosEnum.NOCHE.getHoraHasta();
        }

        Turno turno = new Turno(turnoDTO.getDescripcion().toLowerCase(), horaDesde,
                horaHasta);

        return turnoRepository.save(turno);
    }

    public Turno findById(Long id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException(
                        "Id turno incorrecto. No se encontro el turno indicado."));
    }

    public List<Turno> findAll() {
        return turnoRepository.findAll();
    }

    public void delete(Long id) {
        findById(id);
        turnoRepository.deleteById(id);
    }
}
