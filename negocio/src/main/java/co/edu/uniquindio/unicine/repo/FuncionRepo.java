package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.dto.FuncionDTO;
import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionRepo extends JpaRepository<Funcion,Integer> {

@Query("select f from Funcion f where f.codigo = :codigoFuncion")
    String obtenerNombrePelicula(Integer codigoFuncion);


@Query("select new co.edu.uniquindio.unicine.dto.FuncionDTO(f.pelicula.nombre, f.pelicula.estado, f.pelicula.imagen, f.sala.codigo, f.sala.teatro.direccion, f.sala.teatro.ciudad.nombre,f.horario) from Funcion f where f.pelicula.codigo = :codigoPelicula")
    List<FuncionDTO> listarFunciones(Integer codigoPelicula);

    @Query ("select f from Funcion f where f.sala.teatro.ciudad.codigo = :codigoCiudad")
    List<Funcion> listarFuncionesPorCiudad (Integer codigoCiudad);
}
