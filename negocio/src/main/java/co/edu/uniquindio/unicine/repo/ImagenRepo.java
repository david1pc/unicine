package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepo extends JpaRepository<Imagen, Integer> {

    @Query("select i from Imagen i where i.codigo=:id")
    Imagen eliminarById(Integer id);
}
