package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Administrador;
import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorTeatroRepo extends JpaRepository<AdministradorTeatro,Integer> {
    @Query("select c from AdministradorTeatro c where c.correo = :correo and c.password = :password")
    Optional<AdministradorTeatro> comprobarAutenticacion(String correo, String password);
    AdministradorTeatro findByCorreoAndPassword(String correo, String clave);

    Optional<AdministradorTeatro> findByUsername(String username);

    Optional<Object> findByCorreo(String correo);

    @Query("select ad from AdministradorTeatro ad where ad.codigo<>:codigo and ad.correo = :correo")
    Optional<Object> findByCorreoActualizar(String correo, Integer codigo);

    @Query("select ad from AdministradorTeatro ad where ad.codigo<>:codigo and ad.username = :username")
    Optional<Administrador> findByUsernameActualizar(String username, Integer codigo);
}
