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
public class Cliente extends Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    @Column(nullable = false, unique = true, length = 24)
    private String cedula;
    private Boolean estado;
    @ElementCollection
    private List<String> telefonos;
    private String imagen_perfil;
    @ToString.Exclude
    @OneToMany(mappedBy = "cliente")
    private List<Compra>compras;
    @ToString.Exclude
    @OneToMany(mappedBy = "cliente")
    private List<CuponCliente>cuponClientes;

    public Cliente(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String correo,
                   String password, String cedula, Boolean estado, String imagen_perfil, List<String> telefonos){
        super(primerNombre, segundoNombre, primerApellido, segundoApellido, correo, password);
        this.cedula = cedula;
        this.imagen_perfil = imagen_perfil;
        this.estado = estado;
        this.telefonos = telefonos;
    }

    public Cliente(String primerNombre, String primerApellido, String correo,
                   String password, String cedula, Boolean estado, String imagen_perfil, List<String> telefonos){
        super(primerNombre, primerApellido, correo, password);
        this.cedula = cedula;
        this.imagen_perfil = imagen_perfil;
        this.estado = estado;
        this.telefonos = telefonos;
    }

}
