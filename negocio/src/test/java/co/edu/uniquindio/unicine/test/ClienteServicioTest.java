package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ClienteServicioTest {

    @Autowired
    private ClienteServicio clienteServicio;

    @Test
    @Sql("classpath:dataset.sql")
    public void registrarClienteTest(){
        Cliente cliente = new Cliente(10,"David", "Rodriguez", "juanfeewf@email.com", "1094", "1097584", true, "asdf", null);

        try {
            Cliente clienteNuevo = clienteServicio.registrarCliente(cliente);
            Assertions.assertNotNull(clienteNuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarClienteTest(){
        try {
            Cliente cliente = clienteServicio.obtenerCliente(1);
            cliente.setPrimerNombre("Pepito");
            Cliente nuevo = clienteServicio.actualizarCliente(cliente);
            Assertions.assertEquals("Pepito", nuevo.getPrimerNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarClienteTest(){
        try {
            clienteServicio.eliminarCliente(1);
            Cliente cliente = clienteServicio.obtenerCliente(1);
            Assertions.assertNotNull(cliente);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarClientesTest(){
        List<Cliente> lista = clienteServicio.listarClientes();
        lista.forEach(System.out::println);
    }
}
