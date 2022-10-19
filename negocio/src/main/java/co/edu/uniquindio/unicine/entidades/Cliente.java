package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private String cedula;
    private String nombre;
    private String correo;
    private String password;
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
}
