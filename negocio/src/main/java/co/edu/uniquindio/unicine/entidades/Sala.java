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
public class Sala implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private String nombre;
    @ManyToOne
    private Teatro teatro;
    @ManyToOne
    private DistribucionSillas distribucionSillas;
    @ToString.Exclude
    @OneToMany(mappedBy = "sala")
    private List<Funcion>funciones;
}
