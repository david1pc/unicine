package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.dto.FuncionDTO;
import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface FuncionRepo extends JpaRepository<Funcion,Integer> {

@Query("select f from Funcion f where f.codigo = :codigoFuncion")
    String obtenerNombrePelicula(Integer codigoFuncion);


@Query("select new co.edu.uniquindio.unicine.dto.FuncionDTO(f.pelicula.nombre, f.pelicula.estado, f.pelicula.imagen, f.sala.codigo, f.sala.teatro.direccion, f.sala.teatro.ciudad.nombre,f.horario) from Funcion f where f.pelicula.codigo = :codigoPelicula")
    List<FuncionDTO> listarFunciones(Integer codigoPelicula);

    @Query ("select f from Funcion f where f.sala.teatro.ciudad.codigo = :codigoCiudad")
    List<Funcion> listarFuncionesPorCiudad (Integer codigoCiudad);

    @Query("select f from Funcion f join Horario h on f.horario.codigo = h.codigo join Sala s on s.codigo = f.sala.codigo join Teatro t on t.codigo = s.teatro.codigo where h.hora = :hora and  s.codigo = :salaCodigo and t.codigo = :teatroCodigo")
    List<Funcion> verificarHorarioFuncion(LocalTime hora, Integer salaCodigo, Integer teatroCodigo);
}
