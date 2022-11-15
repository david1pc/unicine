package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.ImagenRepo;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class AdminServicioTest {

    @Autowired
    private AdminServicio adminServicio;

    @Autowired
    private ImagenRepo imagenRepo;

    // ciudades
    @Test
    @Sql("classpath:dataset.sql")
    public void crearCiudadTest(){
        Ciudad ciudad = Ciudad.builder().codigo(6).nombre("Leticia").build();
        try {
            Ciudad ciudadNueva = adminServicio.crearCiudad(ciudad);
            Assertions.assertNotNull(ciudadNueva);
        } catch (Exception e) {
            Assertions.assertTrue(false);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCiudadTest(){
        try {
            Ciudad ciudad = adminServicio.obtenerCiudad(1);
            Assertions.assertNotNull(ciudad);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Películas
    @Test
    @Sql("classpath:dataset.sql")
    public void crearPeliculaTest(){
        Imagen imagen = (Imagen) this.imagenRepo.findById(1).orElse(null);
        Pelicula pelicula = Pelicula.builder().codigo(10).nombre("Fragmentado").sinopsis("Kevin, un hombre con 23 personalidades, secuestra a 3 chicas jóvenes " +
                "y las mantiene retenidas en un sótano").imagen(imagen).genero(Genero.TERROR).estado(true).reparto("James MCavoy").build();
        try {
            Pelicula peliculaNueva = adminServicio.crearPelicula(pelicula, null);
            System.out.println(peliculaNueva);
            Assertions.assertNotNull(peliculaNueva);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarPeliculaTest(){
        try {
            Pelicula pelicula = adminServicio.obtenerPelicula(1);
            pelicula.setNombre("Predestinación");
            Pelicula nueva = adminServicio.actualizarPelicula(pelicula, null);
            Assertions.assertEquals("Predestinación", nueva.getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarPeliculaTest(){
        try {
            adminServicio.eliminarPelicula(1);
            Pelicula pelicula = adminServicio.obtenerPelicula(1);
            Assertions.assertNotNull(pelicula);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarPeliculasTest(){
        List<Pelicula> lista = adminServicio.listarPeliculas();
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPeliculaTest(){
        try {
            Pelicula pelicula = adminServicio.obtenerPelicula(1);
            Assertions.assertNotNull(pelicula);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Cupones
    @Test
    @Sql("classpath:dataset.sql")
    public void crearCuponesTest(){
        Cupon cupon = Cupon.builder().codigo(10).descripcion("descuentinho").descuento(10).build();
        try {
            Cupon cuponNuevo = adminServicio.crearCupon(cupon);
            Assertions.assertNotNull(cuponNuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarCuponTest(){
        try {
            Cupon cupon = adminServicio.obtenerCupon(1);
            cupon.setDescripcion("Holamndaa");
            Cupon cuponNuevo = adminServicio.actualizarCupon(cupon);
            Assertions.assertEquals("Holamndaa", cuponNuevo.getDescripcion());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarCuponTest(){
        try {
            adminServicio.eliminarCupon(1);
            Cupon cupon = adminServicio.obtenerCupon(1);
            Assertions.assertNotNull(cupon);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCuponesTest(){
        List<Cupon> lista = adminServicio.listarCupones();
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCuponTest(){
        try {
            Cupon cupon = adminServicio.obtenerCupon(1);
            Assertions.assertNotNull(cupon);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Confiteria
    @Test
    @Sql("classpath:dataset.sql")
    public void crearConfiteriaTest(){
        Confiteria confiteria = Confiteria.builder().codigo(20).nombre("Tostacos").precio(30000.0).descripcion("Los tostacos saben rico").build();
        try {
            Confiteria confiteriaNueva = adminServicio.crearConfiteria(confiteria, null);
            Assertions.assertNotNull(confiteriaNueva);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarConfiteriaTest(){
        try {
            Confiteria confiteria = adminServicio.obtenerConfiteria(1);
            confiteria.setDescripcion("yumii");
            Confiteria confiteriaNueva = adminServicio.actualizarConfiteria(confiteria, null);
            Assertions.assertEquals("yumii", confiteriaNueva.getDescripcion());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarConfiteriaTest(){
        try {
            adminServicio.eliminarConfiteria(1);
            Confiteria confiteria = adminServicio.obtenerConfiteria(1);
            Assertions.assertNotNull(confiteria);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarConfiteriaTest(){
        List<Confiteria> lista = adminServicio.listarConfiteria();
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerConfiteriaTest(){
        try {
            Confiteria confiteria = adminServicio.obtenerConfiteria(1);
            Assertions.assertNotNull(confiteria);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Combos
    @Test
    @Sql("classpath:dataset.sql")
    public void crearComboTest(){
        Combo combo = Combo.builder().codigo(20).nombre("Tostacos con gaseosa").precio(30000.0).descripcion("Los tostacos saben rico y con gaseosa más").build();
        try {
            Combo comboNuevo = adminServicio.crearCombo(combo, null);
            Assertions.assertNotNull(comboNuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarComboTest(){
        try {
            Combo combo = adminServicio.obtenerCombo(1);
            combo.setDescripcion("yumii");
            Combo comboNuevo = adminServicio.actualizarCombo(combo, null);
            Assertions.assertEquals("yumii", comboNuevo.getDescripcion());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarComboTest(){
        try {
            adminServicio.eliminarCombo(1);
            Combo combo = adminServicio.obtenerCombo(1);
            Assertions.assertNotNull(combo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCombosTest(){
        List<Combo> lista = adminServicio.listarCombos();
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerComboTest(){
        try {
            Combo combo = adminServicio.obtenerCombo(1);
            Assertions.assertNotNull(combo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Administradores de teatros
    @Test
    @Sql("classpath:dataset.sql")
    public void crearAdminTeatroTest(){
        AdministradorTeatro admin = AdministradorTeatro.builder().codigo(10).primerNombre("juanito").primerApellido("alimaña").
                correo("juanitoali@email.com").password("123").
                build();
        try {
            AdministradorTeatro adminNuevo = adminServicio.crearAdministradorTeatro(admin);
            Assertions.assertNotNull(adminNuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarAdminTeatroTest(){
        try {
            AdministradorTeatro admin = adminServicio.obtenerAdministradorTeatro(1);
            admin.setPrimerNombre("Carlos");
            AdministradorTeatro adminNuevo = adminServicio.actualizarAdministradorTeatro(admin);
            Assertions.assertEquals("Carlos", adminNuevo.getPrimerNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarAdminTeatroTest(){
        try {
            adminServicio.eliminarAdministradorTeatro(1);
            AdministradorTeatro admin = adminServicio.obtenerAdministradorTeatro(1);
            Assertions.assertNotNull(admin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarAdminTeatroTest() throws Exception {
        List<AdministradorTeatro> lista = adminServicio.listarAdministradoresTeatro();

        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerAdminTeatroTest(){
        try {
            AdministradorTeatro admin = adminServicio.obtenerAdministradorTeatro(1);
            Assertions.assertNotNull(admin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
