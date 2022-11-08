package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Administrador extends Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @OneToOne
    private Rol rol;


    public Administrador(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String correo,
                   String password, Rol rol, String username){
        super(primerNombre, segundoNombre, primerApellido, segundoApellido, correo, password, username);
        this.rol = rol;
    }

    @Builder
    public Administrador(String primerNombre, String primerApellido, String correo,
                   String password, Rol rol, String username){
        super(primerNombre, primerApellido, correo, password, username);
        this.rol = rol;
    }
}
