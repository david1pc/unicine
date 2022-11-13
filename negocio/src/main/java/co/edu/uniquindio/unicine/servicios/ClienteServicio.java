package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ClienteServicio {
    Cliente obtenerCliente(Integer codigoCliente) throws Exception;
    Cliente login(String correo, String password) throws Exception;

    Cliente registrarCliente(Cliente cliente, MultipartFile imagen) throws Exception;
    Cliente actualizarCliente(Cliente cliente, MultipartFile imagen) throws Exception;
    void eliminarCliente(Integer codigoCliente) throws Exception;
    List<Cliente> listarClientes();
    List<Compra> listarHistorialCompras(Integer codigoCliente) throws Exception;
    Compra registrarCompra(Compra compra) throws Exception;
    Optional<Cupon> redimirCupon(Integer codigoCupon) throws Exception;

    void verificarCredenciales(String correo, String cedula, String username) throws Exception;
}
