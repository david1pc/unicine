package co.edu.uniquindio.unicine.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private Boolean estado;
    private String reparto;
    @ToString.Exclude
    @OneToMany(mappedBy = "pelicula")
    private List<Funcion>funciones;

    @OneToOne
    private Imagen imagen;

    @Builder
    public Pelicula(Integer codigo, String nombre, String sinopsis, Genero genero, Boolean estado, String reparto, Imagen imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.estado = estado;
        this.reparto = reparto;
        this.imagen = imagen;
    }
}
