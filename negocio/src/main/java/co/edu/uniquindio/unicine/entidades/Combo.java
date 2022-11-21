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
public class Combo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;
    private String nombre;
    private Double precio;
    private String descripcion;

    @OneToOne
    private Imagen imagen;
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "combo")
    private List<CompraCombo> compraCombos;

    @Builder
    public Combo(Integer codigo, String nombre, Double precio, String descripcion, List<CompraCombo> compraCombos, Imagen imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.compraCombos = compraCombos;
        this.imagen = imagen;
    }
}
