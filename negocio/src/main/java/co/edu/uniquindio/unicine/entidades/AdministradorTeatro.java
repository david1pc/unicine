package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class AdministradorTeatro extends Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @ToString.Exclude
    @OneToMany(mappedBy = "administradorTeatro")
    private List<Teatro> teatros;

    public AdministradorTeatro(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String correo,
                         String password){
        super(primerNombre, segundoNombre, primerApellido, segundoApellido, correo, password);
    }

    public AdministradorTeatro(String primerNombre, String primerApellido, String correo,
                         String password){
        super(primerNombre, primerApellido, correo, password);
    }
}
