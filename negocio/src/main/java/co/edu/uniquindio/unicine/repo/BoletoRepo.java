package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoRepo extends JpaRepository<Boleto,Integer> {
}
