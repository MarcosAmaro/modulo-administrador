package ar.com.unla.api.repositories;

import ar.com.unla.api.models.database.ExamenFinal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenFinalRepository extends JpaRepository<ExamenFinal, Long> {

    @Query("SELECT ex FROM ExamenFinal ex "
            + "INNER JOIN ex.materia m "
            + "INNER JOIN ex.periodoInscripcion pi "
            + "INNER JOIN m.turno t "
            + "INNER JOIN m.periodoInscripcion mpi "
            + "WHERE t.id = :idTurno "
            + "ORDER BY m.anioCarrera, m.cuatrimestre ASC")
    List<ExamenFinal> findFinalsForPDF(long idTurno);

    @Query("SELECT ex FROM ExamenFinal ex "
            + "INNER JOIN ex.materia m "
            + "INNER JOIN m.turno t "
            + "WHERE m.id = :idMateria AND t.id = :idTurno")
    Optional<ExamenFinal> findBySubject(long idMateria, long idTurno);
}
