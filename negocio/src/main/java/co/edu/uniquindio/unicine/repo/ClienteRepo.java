package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Administrador;
import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente,Integer> {
    Optional<Cliente> findByCorreo(String correo);

    Optional<Cliente> findByCedula(String cedula);

    Optional<Cliente> findByUsername(String username);

    @Query("select c from Cliente c where c.codigo<>:codigo and c.correo = :correo")
    Optional<Cliente> findByCorreoActualizar(String correo, Integer codigo);

    @Query("select c from Cliente c where c.codigo<>:codigo and c.correo = :cedula")
    Optional<Cliente> findByCedulaActualizar(String cedula, Integer codigo);

    @Query("select c from Cliente c where c.codigo<>:codigo and c.correo = :username")
    Optional<Cliente> findByUsernameActualizar(String username, Integer codigo);

    Cliente findByCorreoAndPassword(String email, String clave);
    @Query("select c from Cliente c where c.correo = :correo and c.password = :password")
    Optional<Cliente> comprobarAutenticacion(String correo, String password);

    @Query("select c from Cliente c where c.estado = :estado")
    List<Cliente> obtenerPorEstado(Boolean estado, Pageable paginator);

    @Query("select comp from Cliente cli join cli.compras comp where cli.correo = :correo")
    List<Compra> obtenerCompras(String correo);


}
