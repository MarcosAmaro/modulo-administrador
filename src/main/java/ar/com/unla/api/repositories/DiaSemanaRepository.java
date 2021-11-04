package ar.com.unla.api.repositories;

import ar.com.unla.api.models.database.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaSemanaRepository extends JpaRepository<DiaSemana, Long> {

}
