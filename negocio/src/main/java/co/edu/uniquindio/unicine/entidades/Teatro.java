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
public class Teatro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private String direccion;
    private String telefono;
    @ManyToOne
    private Administrador administrador;
    @ManyToOne
    private Ciudad ciudad;
    @OneToMany(mappedBy = "teatro")
    private List<Sala>salas;
}
