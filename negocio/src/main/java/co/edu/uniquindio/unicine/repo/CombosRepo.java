package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Combos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombosRepo extends JpaRepository<Combos,Integer> {
}
