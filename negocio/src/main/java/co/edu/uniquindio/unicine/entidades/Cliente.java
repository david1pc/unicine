package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class Cliente implements Serializable {
    @Id
    private Integer cedula;
    private String nombre;
    private String correo;
    private String password;
    private Boolean estado;
    @ElementCollection
    private List<String> telefonos;
    private String imagen_perfil;
    @OneToMany(mappedBy = "cliente")
    private List<Compra>compras;
    @OneToMany(mappedBy = "cliente")
    private List<CuponCliente>cuponClientes;
}
