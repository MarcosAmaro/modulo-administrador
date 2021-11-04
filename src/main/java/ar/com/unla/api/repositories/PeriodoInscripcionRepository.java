package ar.com.unla.api.repositories;

import ar.com.unla.api.models.database.PeriodoInscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodoInscripcionRepository extends JpaRepository<PeriodoInscripcion, Long> {

}
