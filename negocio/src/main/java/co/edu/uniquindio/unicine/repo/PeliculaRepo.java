package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepo extends JpaRepository<Pelicula,Integer> {

    @Query("select distinct p from Funcion f join Sala s on f.sala.codigo = s.codigo join Teatro t on t.codigo = s.teatro.codigo join Ciudad c on t.ciudad.codigo = c.codigo join Pelicula p on f.pelicula.codigo = p.codigo where c.codigo = :idCiudad and t.codigo = :idTeatro")
    List<Pelicula> obtener_peliculas(Integer idCiudad, Integer idTeatro);

    @Query("select distinct p from Funcion f join Sala s on f.sala.codigo = s.codigo join Teatro t on t.codigo = s.teatro.codigo join Ciudad c on t.ciudad.codigo = c.codigo join Pelicula p on f.pelicula.codigo = p.codigo where c.codigo = :idCiudad")
    List<Pelicula> obtener_peliculas_ciudad(Integer idCiudad);
}
