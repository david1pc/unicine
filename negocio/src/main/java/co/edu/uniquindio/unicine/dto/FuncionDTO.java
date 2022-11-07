package co.edu.uniquindio.unicine.dto;

import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Imagen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class FuncionDTO {
    private String nombrepelicula;
    private Boolean estadoPelicula;
    private Imagen imagen;
    private Integer numeroSala;
    private String direccionTeatro;
    private String nombreCiudad;
    private Horario horario;
}
