package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionRepo extends JpaRepository<Funcion,Integer> {
}
