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
    @EqualsAndHashCode.Include
    private Integer codigo;
    @Column(nullable = false, unique = true, length = 24)
    private String cedula;
    private Boolean estado;
    @ElementCollection
    private List<String> telefonos;

    @OneToOne
    private Imagen imagen;

    @OneToOne
    private Rol rol;

    @ToString.Exclude
    @OneToMany(mappedBy = "cliente")
    private List<Compra>compras;
    @ToString.Exclude
    @OneToMany(mappedBy = "cliente")
    private List<CuponCliente>cuponClientes;

    public Cliente(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String correo,
                   String password, String cedula, Boolean estado, Rol rol, String username, List<String> telefonos){
        super(primerNombre, segundoNombre, primerApellido, segundoApellido, correo, password, username);
        this.cedula = cedula;
        this.estado = estado;
        this.telefonos = telefonos;
        this.rol = rol;
    }

    @Builder
    public Cliente(Integer codigo, String primerNombre, String primerApellido, String correo,
                   String password, String cedula, Boolean estado, Imagen imagen, String username, Rol rol, List<String> telefonos){
        super(primerNombre, primerApellido, correo, password, username);
        this.codigo = codigo;
        this.cedula = cedula;
        this.imagen = imagen;
        this.estado = estado;
        this.telefonos = telefonos;
        this.rol = rol;
    }

}
