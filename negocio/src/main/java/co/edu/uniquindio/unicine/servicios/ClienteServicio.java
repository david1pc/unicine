package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClienteServicio {
    Cliente obtenerCliente(Integer codigoCliente) throws Exception;
    Cliente login(String correo, String password) throws Exception;

    Cliente registrarCliente(Cliente cliente, MultipartFile imagen) throws Exception;

    Boolean verificarEstadoCliente(String username, String rol);
    Cliente actualizarCliente(Cliente cliente, MultipartFile imagen) throws Exception;

    void eliminarCliente(Integer codigoCliente) throws Exception;
    List<Cliente> listarClientes();
    List<Compra> listarHistorialCompras(Integer codigoCliente) throws Exception;
    List<Compra> listarHistorialComprasUsername(String username) throws Exception;
    Compra registrarCompra(Compra compra) throws Exception;
    Optional<Cupon> redimirCupon(Integer codigoCupon) throws Exception;

    List<Funcion> obtener_funciones(Integer idCiudad, Integer idTeatro) throws Exception;

    List<Funcion> obtener_funciones_ciudad(Integer idCiudad) throws Exception;

    List<Funcion> obtener_funciones_pelicula(Integer idCiudad, Integer idPelicula, LocalDate fecha, String dia) throws Exception;

    List<Pelicula> obtener_peliculas(Integer idCiudad, Integer idTeatro) throws Exception;
    List<Pelicula> obtener_peliculas_ciudad(Integer idCiudad) throws Exception;

    void verificarCredenciales(String correo, String cedula, String username) throws Exception;

    Entrada crearEntrada(Entrada entrada) throws Exception;
    CompraCombo crearCompraCombo(CompraCombo compraCombo) throws Exception;

    CompraConfiteria crearCompraConfiteria(CompraConfiteria compraConfiteria) throws Exception;

    CuponCliente crearCuponCliente(CuponCliente cuponCliente) throws Exception;
    Cliente actualizarClienteVerificado(Cliente cliente, String passwdEnc) throws Exception;
}
