package co.edu.uniquindio.unicine.dto;

import co.edu.uniquindio.unicine.entidades.Horario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class FuncionDTO {
    private String nombrepelicula;
    private Boolean estadoPelicula;
    private String url_img;
    private Integer numeroSala;
    private String direccionTeatro;
    private String nombreCiudad;
    private Horario horario;
}
