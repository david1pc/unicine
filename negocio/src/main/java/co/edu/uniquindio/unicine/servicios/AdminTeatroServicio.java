package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Horario;

public interface AdminTeatroServicio {
    Horario crearHorario(Horario horario);
    Funcion crearFuncion(Funcion funcion);
}
