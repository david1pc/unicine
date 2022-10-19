package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
        Cliente cliente = new Cliente();
        Cliente guardado = clienteRepo.save(cliente);
        Assertions.assertNotNull(guardado);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminar(){
    Cliente buscado = clienteRepo.findById(1224544).orElse(null);
    clienteRepo.delete(buscado);
    Assertions.assertNull(clienteRepo.findById(1224544).orElse(null));
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void actualizar(){
    Cliente guardado = clienteRepo.findById(2151541).orElse(null);
    guardado.setCorreo("pedro@email.com");
    Cliente nuevo = clienteRepo.save(guardado);
    Assertions.assertEquals("marianito_1@email.com",nuevo.getCorreo());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtener(){
        Optional<Cliente> buscado=clienteRepo.findById(2151541);
        Assertions.assertNotNull(buscado.orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listar(){
        List<Cliente> lista = clienteRepo.findAll();
        lista.forEach(System.out::println);
    }
}