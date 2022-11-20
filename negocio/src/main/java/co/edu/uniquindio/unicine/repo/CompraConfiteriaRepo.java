package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.CompraConfiteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraConfiteriaRepo extends JpaRepository<CompraConfiteria,Integer> {
}
