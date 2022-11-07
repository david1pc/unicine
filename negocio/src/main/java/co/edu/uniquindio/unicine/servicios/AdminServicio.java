package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;

import java.util.List;

public interface AdminServicio {

    // Gestionar ciudades
    Ciudad crearCiudad(Ciudad ciudad) throws Exception;

    Ciudad obtenerCiudad(Integer codigo) throws Exception;

    // Gestionar Películas

    Pelicula crearPelicula (Pelicula pelicula) throws Exception;
    Pelicula actualizarPelicula (Pelicula pelicula) throws Exception;
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
    Confiteria crearConfiteria (Confiteria confiteria)throws Exception;
    Confiteria actualizarConfiteria (Confiteria confiteria)throws Exception;
    void eliminarConfiteria (Integer codigo)throws Exception;
    List <Confiteria> listarConfiteria ();
    Confiteria obtenerConfiteria (Integer codigo)throws Exception;

    // Gestionar Combos
    Combo crearCombo (Combo combo)throws Exception;
    Combo actualizarCombo (Combo combo)throws Exception;
    void eliminarCombo (Integer codigo)throws Exception;
    List <Combo> listarCombos ();
    Combo obtenerCombo (Integer codigo)throws Exception;


    // Gestionar Administradores de teatros
    AdministradorTeatro crearAdministradorTeatro (AdministradorTeatro administradorTeatro)throws Exception;
    AdministradorTeatro actualizarAdministradorTeatro (AdministradorTeatro administradorTeatro)throws Exception;
    void eliminarAdministradorTeatro (Integer codigo)throws Exception;
    List <AdministradorTeatro> listarAdministradoresTeatro ();
    AdministradorTeatro obtenerAdministradorTeatro (Integer codigo)throws Exception;
}
