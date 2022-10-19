package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepo extends JpaRepository<Compra,Integer> {
}
