package co.edu.uniquindio.unicine.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private AdministradorTeatro administradorTeatro;
    @ManyToOne
    @JsonIgnore
    private Ciudad ciudad;
    @ToString.Exclude
    @OneToMany(mappedBy = "teatro")
    private List<Sala>salas;

    @Builder
    public Teatro(Integer codigo, String direccion, String telefono) {
        this.codigo = codigo;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}
