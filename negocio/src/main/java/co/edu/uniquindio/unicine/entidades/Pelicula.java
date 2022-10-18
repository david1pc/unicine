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
public class Pelicula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private String nombre;
    private String sinopsis;
    private String url_trailer;
    private String url_img;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String estado;
    private String reparto;
    @OneToMany(mappedBy = "pelicula")
    private List<Funcion>funciones;
}
