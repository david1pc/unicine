package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminServicio {

    // Gestionar ciudades
    Ciudad crearCiudad(Ciudad ciudad) throws Exception;

    Ciudad obtenerCiudad(Integer codigo) throws Exception;

    // Gestionar Películas

    Pelicula crearPelicula (Pelicula pelicula, MultipartFile imagen) throws Exception;
    Pelicula actualizarPelicula (Pelicula pelicula, MultipartFile imagen) throws Exception;
    void eliminarPelicula (Integer codigo) throws Exception;
    List <Pelicula> listarPeliculas ();
    Pelicula obtenerPelicula (Integer codigo) throws Exception;

    // Gestionar Cupones
    Cupon crearCupon(Cupon cupon)throws Exception;
    Cupon actualizarCupon(Cupon cupon)throws Exception;
    void eliminarCupon (Integer codigo)throws Exception;
    List <Cupon> listarCupones ();
    Cupon obtenerCupon (Integer codigo)throws Exception;

    // Gestionar Confiteria
    Confiteria crearConfiteria (Confiteria confiteria, MultipartFile imagen)throws Exception;
    Confiteria actualizarConfiteria (Confiteria confiteria, MultipartFile imagen)throws Exception;
    void eliminarConfiteria (Integer codigo)throws Exception;
    List <Confiteria> listarConfiteria ();
    Confiteria obtenerConfiteria (Integer codigo)throws Exception;

    // Gestionar Combos
    Combo crearCombo (Combo combo, MultipartFile imagen)throws Exception;
    Combo actualizarCombo (Combo combo, MultipartFile imagen)throws Exception;
    void eliminarCombo (Integer codigo)throws Exception;
    List <Combo> listarCombos ();
    Combo obtenerCombo (Integer codigo)throws Exception;


    // Gestionar Administradores de teatros
    AdministradorTeatro crearAdministradorTeatro (AdministradorTeatro administradorTeatro)throws Exception;
    AdministradorTeatro actualizarAdministradorTeatro (AdministradorTeatro administradorTeatro)throws Exception;
    void eliminarAdministradorTeatro (Integer codigo)throws Exception;
    List <AdministradorTeatro> listarAdministradoresTeatro () throws Exception;
    AdministradorTeatro obtenerAdministradorTeatro (Integer codigo)throws Exception;
}
