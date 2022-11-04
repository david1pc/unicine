package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteTest {
    @Autowired
    private ClienteRepo clienteRepo;
    @Test
    @Sql("classpath:dataset.sql")
    public void registrar(){
        Cliente cliente = new Cliente(10, "David", "Rodriguez", "asdd@gmail.com", "1094", "1094", true, "asdf", null);
        //cliente.builder().cedula("123456").("David").correo("david@gmail.com").estado(true).imagen_perfil("rgregegr")
                //.password("dadfr").build();

        Cliente guardado = clienteRepo.save(cliente);
        Assertions.assertNotNull(guardado);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminar(){
    Cliente buscado = clienteRepo.findById(1).orElse(null);
    clienteRepo.delete(buscado);
    Assertions.assertNull(clienteRepo.findById(1).orElse(null));
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void actualizar(){
    Cliente guardado = clienteRepo.findById(1).orElse(null);
    guardado.setCorreo("pedro@email.com");
    Cliente nuevo = clienteRepo.save(guardado);
    Assertions.assertEquals("marianito_1@email.com",nuevo.getCorreo());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listar(){
        List<Cliente> lista = clienteRepo.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtener(){
        Optional<Cliente> buscado=clienteRepo.findById(3);
        Assertions.assertNotNull(buscado.orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPorCorreo(){
        Cliente buscado= clienteRepo.findByCorreo("juan@email.com").orElse(null);
        Assertions.assertNotNull(buscado);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void findByCorreoAndPassword(){
        Optional<Cliente> buscado = Optional.ofNullable(clienteRepo.findByCorreoAndPassword("juan@email.com", "51321354"));
        Assertions.assertNotNull(buscado.orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void comprobarAutenticacion(){
        Optional<Cliente> buscado = clienteRepo.comprobarAutenticacion("juan@email.com", "51321354");
        Assertions.assertNotNull(buscado.orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPorEstado(){
        List<Cliente> clientes = clienteRepo.obtenerPorEstado(true, PageRequest.of(0,3));
        clientes.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCompras(){
        List<Compra> compras = clienteRepo.obtenerCompras("juan@email.com");
        compras.forEach(System.out::println);
    }


}