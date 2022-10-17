package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends JpaRepository<Login,Integer> {
}