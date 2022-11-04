package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepo extends JpaRepository<Administrador,Integer> {

    Administrador findByCorreoAndPassword(String correo, String clave);

    @Query("select c from Administrador c where c.correo = :correo and c.password = :password")
    Optional<Administrador> comprobarAutenticacion(String correo, String password);


}
