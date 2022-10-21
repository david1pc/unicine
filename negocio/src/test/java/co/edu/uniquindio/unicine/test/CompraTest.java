package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import co.edu.uniquindio.unicine.repo.CompraRepo;
import co.edu.uniquindio.unicine.repo.CuponClienteRepo;
import co.edu.uniquindio.unicine.repo.FuncionRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompraTest {
    @Autowired
    private CompraRepo compraRepo;

    @Autowired
    private ClienteRepo clienteRepo;

    @Autowired
    private FuncionRepo funcionRepo;

    @Autowired
    private CuponClienteRepo cuponClienteRepo;

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCompraPorFecha(){
        //LocalDate fecha_compra = new LocalDate();
        //List<Compra>compras = compraRepo.ObtenerCompra("2022-10-20");

        //compras.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void findById(){
        Optional<Compra> compra = compraRepo.findById(6);
        //List<Compra> comprasCon = compras.stream().toList();
        Assertions.assertNotNull(compra.orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void registrarCompra(){
        Optional<Funcion> funcion = funcionRepo.findById(1);
        Optional<Cliente> cliente = clienteRepo.findById(1);
        Optional<CuponCliente> cuponCliente = cuponClienteRepo.findById(1);
        Compra compra = new Compra(MedioPago.EFECTIVO, LocalDate.of(2022, 10, 22), 1000.00, funcion.get(), cuponCliente.get(), cliente.get());
        Compra compraRegistrada = compraRepo.save(compra);
        Assertions.assertNotNull(compraRegistrada);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminar(){
        Compra buscado = compraRepo.findById(1).orElse(null);
        compraRepo.delete(buscado);
        Assertions.assertNull(compraRepo.findById(1).orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listar(){
        List<Compra> lista = compraRepo.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCliente(){
        List<Cliente> clientes = compraRepo.obtenerCliente(1);
        clientes.forEach(System.out::println);
    }






}
