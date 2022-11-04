package co.edu.uniquindio.unicine.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Login {
    private String correo;
    private String password;

    public Login(String correo, String password){
        this.correo = correo;
        this.password = password;
    }
}
