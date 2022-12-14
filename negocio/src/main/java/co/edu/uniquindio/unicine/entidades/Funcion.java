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
public class Funcion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private Double precio;
    @ManyToOne
    private Sala sala;
    @ManyToOne
    private Horario horario;
    @ManyToOne
    private Pelicula pelicula;
    @ToString.Exclude
    @OneToMany(mappedBy = "funcion")
    private List<Compra>compras;

    @Builder
    public Funcion(Integer codigo, Double precio) {
        this.codigo = codigo;
        this.precio = precio;
    }
}
