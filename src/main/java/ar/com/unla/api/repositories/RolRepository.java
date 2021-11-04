package ar.com.unla.api.repositories;

import ar.com.unla.api.models.database.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    List<Rol> findAllByOrderByDescripcionAsc();
}
