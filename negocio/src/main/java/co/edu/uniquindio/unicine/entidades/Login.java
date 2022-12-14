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
public class Login implements Serializable {
    private String username;
    private String password;

    public Login(String username, String password){
        this.username = username;
        this.password = password;
    }
}
