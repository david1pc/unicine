package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class Administrador implements Serializable {
    @Id
    private Integer codigo;
    private String correo;
    private String password;
    @OneToMany(mappedBy = "administrador")
    private List<Teatro> teatros;
}
