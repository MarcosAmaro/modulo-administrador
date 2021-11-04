package ar.com.unla.api.repositories;

import ar.com.unla.api.models.database.Materia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    List<Materia> findAllByOrderByNombreAsc();

    @Query("SELECT m FROM Materia m "
            + "INNER JOIN m.profesor p "
            + "INNER JOIN m.periodoInscripcion pi "
            + "INNER JOIN m.turno t "
            + "WHERE t.id = :idTurno "
            + "ORDER BY m.anioCarrera, m.cuatrimestre ASC")
    List<Materia> findSubjectsForPDF(long idTurno);
}
