package co.edu.uniquindio.unicine.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class AdministradorTeatro extends Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "administradorTeatro")
    private List<Teatro> teatros;

    @OneToOne
    private Rol rol;

    @Builder
    public AdministradorTeatro(Integer codigo, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String correo,
                         String password, Rol rol, String username){
        super(primerNombre, segundoNombre, primerApellido, segundoApellido, correo, password, username);
        this.codigo = codigo;
        this.rol = rol;
    }

    public AdministradorTeatro(String primerNombre, String primerApellido, String correo,
                         String password, Rol rol, String username){
        super(primerNombre, primerApellido, correo, password, username);
        this.rol = rol;
    }
}
