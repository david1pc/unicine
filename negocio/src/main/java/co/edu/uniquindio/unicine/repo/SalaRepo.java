package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepo extends JpaRepository<Sala,Integer> {
}
