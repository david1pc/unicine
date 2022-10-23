package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Genero;
import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class AdminTeatroTest {

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;


    // Horarios
    @Test
    @Sql("classpath:dataset.sql")
    public void crearHorarioTest(){
        Horario horario = Horario.builder().dia("Lunes").fecha_inicio(LocalDate.parse("2022-10-22")).fecha_fin(LocalDate.parse("2022-11-21")).build();
        try {
            Horario horarioNuevo = adminTeatroServicio.crearHorario(horario);
            Assertions.assertNotNull(horarioNuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarHorarioTest(){
        try {
            Horario horario = adminTeatroServicio.obtenerHorario(1);
            horario.setDia("Lunes");
            Horario horarioNuevo = adminTeatroServicio.actualizarHorario(horario);
            Assertions.assertEquals("Lunes", horarioNuevo.getDia());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarHorarioTest(){
        try {
            adminTeatroServicio.eliminarHorario(1);
            Horario horario = adminTeatroServicio.obtenerHorario(1);
            Assertions.assertNotNull(horario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarHorariosTest(){
        List<Horario> lista = adminTeatroServicio.listarHorarios();
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerHorarioTest(){
        try {
            Horario horario = adminTeatroServicio.obtenerHorario(1);
            Assertions.assertNotNull(horario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Funciones
    @Test
    @Sql("classpath:dataset.sql")
    public void crearFuncionTest(){
        Funcion funcion = Funcion.builder().codigo(10).precio(54000.0).build();
        try {
            Funcion funcionNueva = adminTeatroServicio.crearFuncion(funcion);
            Assertions.assertNotNull(funcionNueva);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarFuncionTest(){
        try {
            Funcion funcion = adminTeatroServicio.obtenerFuncion(1);
            funcion.setPrecio(88888.88);
            Funcion funcionNueva = adminTeatroServicio.actualizarFuncion(funcion);
            Assertions.assertEquals(88888.88, funcionNueva.getPrecio());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarFuncionTest(){
        try {
            adminTeatroServicio.eliminarFuncion(1);
            Funcion funcion = adminTeatroServicio.obtenerFuncion(1);
            Assertions.assertNotNull(funcion);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarFuncionesTest(){
        List<Funcion> lista = adminTeatroServicio.listarFunciones();
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerFuncionesTest(){
        try {
            Funcion funcion = adminTeatroServicio.obtenerFuncion(1);
            Assertions.assertNotNull(funcion);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
