package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;

import java.util.List;
import java.util.Optional;

public interface ClienteServicio {
    Cliente obtenerCliente(Integer codigoCliente) throws Exception;
    Cliente login(String correo, String password) throws Exception;
    Auth login2(String correo, String password) throws Exception;
    Cliente registrarCliente(Cliente cliente) throws Exception;
    Cliente actualizarCliente(Cliente cliente) throws Exception;
    void eliminarCliente(Integer codigoCliente) throws Exception;
    List<Cliente> listarClientes();
    List<Compra> listarHistorialCompras(Integer codigoCliente) throws Exception;
    Compra registrarCompra(Compra compra) throws Exception;
    Optional<Cupon> redimirCupon(Integer codigoCupon) throws Exception;

    Auth asignarAuth(Cliente cliente, Administrador administrador, AdministradorTeatro administradorTeatro) throws Exception;
}
