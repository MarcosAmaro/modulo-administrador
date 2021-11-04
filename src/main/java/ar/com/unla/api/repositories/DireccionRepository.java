package ar.com.unla.api.repositories;

import ar.com.unla.api.models.database.Direccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    List<Direccion> findAllByOrderByPaisAsc();
}
