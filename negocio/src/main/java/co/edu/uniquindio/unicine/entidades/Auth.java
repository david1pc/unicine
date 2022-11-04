package co.edu.uniquindio.unicine.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
    private Integer codigo;
    private String correo;
    private Rol rol;
}
