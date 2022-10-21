package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.dto.FuncionDTO;
import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.repo.FuncionRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FuncionTest {
    @Autowired
    FuncionRepo funcionRepo;
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPeliculaFuncion(){
        String nombrePelicula = funcionRepo.obtenerNombrePelicula(1);
        System.out.println(nombrePelicula);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerFunciones(){
        List<FuncionDTO> funciones = funcionRepo.listarFunciones(1);
        funciones.forEach(System.out::println);
        }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminar(){
        Funcion buscado = funcionRepo.findById(1).orElse(null);
        funcionRepo.delete(buscado);
        Assertions.assertNull(funcionRepo.findById(1).orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listar(){
        List<Funcion> lista = funcionRepo.findAll();
        lista.forEach(System.out::println);
    }
}
