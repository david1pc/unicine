package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepo extends JpaRepository<Ciudad,Integer> {
}
