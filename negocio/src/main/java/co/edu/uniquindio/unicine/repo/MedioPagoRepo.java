package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.MedioPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MedioPagoRepo extends JpaRepository<MedioPago,Integer> {
}
