package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToMany(mappedBy = "cupon")
    private List<CuponCliente>cuponClientes;
}
