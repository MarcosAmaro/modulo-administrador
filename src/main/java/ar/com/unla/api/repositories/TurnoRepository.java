package ar.com.unla.api.repositories;

import ar.com.unla.api.models.database.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

}
