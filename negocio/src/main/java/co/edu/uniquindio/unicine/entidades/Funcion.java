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
public class Funcion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private String nombre;
    private String enlace_trailer;
    private String idioma;
    private String reparto;
    private String clasificacion;
    private Double precio;
    @ManyToOne
    private Sala sala;
    @ManyToOne
    private Horario horario;
    @ManyToOne
    private Pelicula pelicula;
    @OneToMany(mappedBy = "funcion")
    private List<Compra>compras;
}
