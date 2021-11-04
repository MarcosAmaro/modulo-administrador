package ar.com.unla.api.repositories;

import ar.com.unla.api.models.database.UsuarioMateria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioMateriaRepository extends JpaRepository<UsuarioMateria, Long> {

    @Query("SELECT usm FROM UsuarioMateria usm "
            + "INNER JOIN usm.usuario u "
            + "INNER JOIN u.rol r "
            + "INNER JOIN usm.materia m "
            + "WHERE m.id = :idMateria AND r.descripcion = 'alumno'")
    List<UsuarioMateria> findStudentBySubject(long idMateria);

    @Query("SELECT usm FROM UsuarioMateria usm "
            + "INNER JOIN usm.usuario u "
            + "INNER JOIN u.rol r "
            + "INNER JOIN usm.materia m "
            + "INNER JOIN m.turno t "
            + "WHERE m.id = :idMateria AND u.id = :idUsuario "
            + "AND LOWER(t.descripcion) = :descripcionTurno")
    Optional<UsuarioMateria> findUserSubject(long idMateria, long idUsuario,
            String descripcionTurno);


    @Query("SELECT usm FROM UsuarioMateria usm INNER JOIN usm.usuario u INNER "
            + "JOIN usm.materia m WHERE u.id = :idUsuario")
    List<UsuarioMateria> findSubjectsByUser(long idUsuario);
}
