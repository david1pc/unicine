package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.Cupon;

import java.util.List;
import java.util.Optional;

public interface ClienteServicio {
    Cliente obtenerCliente(Integer codigoCliente) throws Exception;
    Cliente login(String correo, String password) throws Exception;
    Cliente registrarCliente(Cliente cliente) throws Exception;
    Cliente actualizarCliente(Cliente cliente) throws Exception;
    void eliminarCliente(Integer codigoCliente) throws Exception;
    List<Cliente> listarClientes();
    List<Compra> listarHistorialCompras(Integer codigoCliente) throws Exception;
    Compra registrarCompra(Compra compra) throws Exception;
    Optional<Cupon> redimirCupon(Integer codigoCupon) throws Exception;
}
