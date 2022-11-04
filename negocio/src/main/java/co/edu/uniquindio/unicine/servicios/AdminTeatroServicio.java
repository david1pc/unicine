package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Teatro;

import java.util.List;

public interface AdminTeatroServicio {

    Horario crearHorario(Horario horario)throws Exception;
    Horario actualizarHorario (Horario horario)throws Exception;
    void eliminarHorario (Integer codigo)throws Exception;
    List<Horario> listarHorarios();
    Horario obtenerHorario (Integer codigo)throws Exception;


    Funcion crearFuncion(Funcion funcion)throws Exception;
    Funcion actualizarFuncion(Funcion funcion)throws Exception;
    void eliminarFuncion(Integer codigo)throws Exception;
    List<Funcion> listarFunciones ();
    Funcion obtenerFuncion(Integer codigo)throws Exception;

    Teatro crearTeatro(Teatro teatro)throws Exception;

    Teatro actualizarTeatro(Teatro teatro)throws Exception;

    void eliminarTeatro(Integer codigo)throws Exception;

    List<Teatro> listarTeatros ();

    Teatro obtenerTeatro(Integer codigo)throws Exception;
}
