package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.repo.FuncionRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

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

}
