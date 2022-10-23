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

    public Administrador(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String correo,
                   String password){
        super(primerNombre, segundoNombre, primerApellido, segundoApellido, correo, password);
    }

    @Builder
    public Administrador(String primerNombre, String primerApellido, String correo,
                   String password){
        super(primerNombre, primerApellido, correo, password);
    }
}
