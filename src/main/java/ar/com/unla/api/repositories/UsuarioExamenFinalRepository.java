package ar.com.unla.api.repositories;

import ar.com.unla.api.models.database.UsuarioExamenFinal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioExamenFinalRepository extends JpaRepository<UsuarioExamenFinal, Long> {

    @Query("SELECT uef FROM UsuarioExamenFinal uef "
            + "INNER JOIN uef.usuario u "
            + "INNER JOIN u.rol r "
            + "INNER JOIN uef.examenFinal ef "
            + "INNER JOIN ef.materia m "
            + "WHERE m.id = :idMateria AND r.descripcion = 'alumno'")
    List<UsuarioExamenFinal> findStudentsByFinalExam(long idMateria);

    @Query("SELECT uef FROM UsuarioExamenFinal uef "
            + "INNER JOIN uef.usuario u "
            + "INNER JOIN uef.examenFinal ef "
            + "INNER JOIN ef.materia m "
            + "INNER JOIN m.profesor p "
            + "INNER JOIN m.turno t "
            + "WHERE m.id = :idMateria AND u.id = :idUsuario "
            + "AND LOWER(t.descripcion) = :descripcionTurno")
    Optional<UsuarioExamenFinal> findUserFinalExam(long idMateria, long idUsuario,
            String descripcionTurno);

    @Query("SELECT uef FROM UsuarioExamenFinal uef INNER JOIN uef.usuario u INNER "
            + "JOIN uef.examenFinal ef WHERE u.id = :idUsuario")
    List<UsuarioExamenFinal> findFinalExamsByUser(long idUsuario);
}
