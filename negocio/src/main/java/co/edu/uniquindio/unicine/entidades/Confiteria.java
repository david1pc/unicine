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
public class Confiteria implements Serializable {
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
    @OneToMany(mappedBy = "confiteria")
    private List<CompraConfiteria>compraConfiterias;

    @Builder
    public Confiteria(Integer codigo, String nombre, Double precio, String descripcion, List<CompraConfiteria> compraConfiterias, Imagen imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.compraConfiterias = compraConfiterias;
        this.imagen = imagen;
    }
}
