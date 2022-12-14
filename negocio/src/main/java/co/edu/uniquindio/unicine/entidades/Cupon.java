package co.edu.uniquindio.unicine.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class Cupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private String descripcion;
    private Integer descuento;
    private String criterio;
    private LocalDate vencimiento;
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "cupon")
    private List<CuponCliente>cuponClientes;

    @Builder
    public Cupon(Integer codigo, String descripcion, Integer descuento, String criterio, LocalDate vencimiento, List<CuponCliente> cuponClientes) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.criterio = criterio;
        this.vencimiento = vencimiento;
        this.cuponClientes = cuponClientes;
    }

    public Cupon(Integer codigo, String descripcion, Integer descuento, String criterio, LocalDate vencimiento) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.criterio = criterio;
        this.vencimiento = vencimiento;
    }
}
