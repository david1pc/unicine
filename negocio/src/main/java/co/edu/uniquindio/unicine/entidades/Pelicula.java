package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
    private Boolean estado;
    private String reparto;
    @ToString.Exclude
    @OneToMany(mappedBy = "pelicula")
    private List<Funcion>funciones;

    public Pelicula(String nombre, String sinopsis, String url_img, Genero genero, Boolean estado, String reparto) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.url_img = url_img;
        this.genero = genero;
        this.estado = estado;
        this.reparto = reparto;
    }
}
