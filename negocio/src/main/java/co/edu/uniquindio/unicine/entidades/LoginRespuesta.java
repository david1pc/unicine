package co.edu.uniquindio.unicine.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginRespuesta implements Serializable {
    private String jwttoken;
    private String username;

    public LoginRespuesta(String name, String jwttoken) {
        this.username = name;
        this.jwttoken = jwttoken;
    }
}
