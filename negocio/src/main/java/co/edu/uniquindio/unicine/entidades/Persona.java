package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@MappedSuperclass
@ToString
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(nullable = false, length = 100)
    private String primerNombre;

    @Column(length = 100)
    private String segundoNombre;

    @Column(nullable = false, length = 100)
    private String primerApellido;

    @Column(length = 100)
    private String segundoApellido;

    @Email
    @Column(nullable = false, unique = true, length = 200)
    private String correo;

    @Column(length = 100)
    private String username;

    @ToString.Exclude
    @Column(nullable = false, length = 1000)
    private String password;

    public Persona(String primerNombre, String primerApellido, String correo, String password, String username) {
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.correo = correo;
        this.password = password;
        this.username = username;
    }

    public Persona(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String correo, String password, String username) {
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correo = correo;
        this.password = password;
        this.username = username;
    }
}
