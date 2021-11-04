package ar.com.unla.api.repositories;

import ar.com.unla.api.models.database.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u "
            + "INNER JOIN u.rol r "
            + "INNER JOIN u.direccion d "
            + "WHERE LOWER(r.descripcion) = 'alumno'")
    List<Usuario> findStudentUsers();

    @Query("SELECT u FROM Usuario u "
            + "INNER JOIN u.rol r "
            + "INNER JOIN u.direccion d "
            + "WHERE LOWER(r.descripcion) = 'docente'")
    List<Usuario> findTeacherUsers();

    @Query("SELECT u FROM Usuario u "
            + "INNER JOIN u.rol r "
            + "INNER JOIN u.direccion d "
            + "WHERE LOWER(u.email) = :email AND u.password = :password")
    Optional<Usuario> findUserByPassAndEmail(String email, String password);
}
