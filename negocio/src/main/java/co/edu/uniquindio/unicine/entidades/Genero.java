package co.edu.uniquindio.unicine.entidades;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

public enum Genero {
    ACCION,
    COMEDIA,
    ROMANCE,
    CIENCIA_FICCION,
    ANIMADA,
    DRAMA,
    TERROR
}
