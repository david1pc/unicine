package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepo extends JpaRepository<Factura,Integer> {
}
