package co.edu.uniquindio.unicine.entidades;

import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class Compra implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    @Enumerated(EnumType.STRING)
    private MedioPago medioPago;
    private LocalDateTime fecha_compra;
    private Double valor_total;
    @ManyToOne
    private Funcion funcion;
    @OneToMany(mappedBy = "compra")
    private List<Entrada>entradas;
    @OneToMany(mappedBy = "compra")
    private List<CompraConfiteria>compraConfiterias;
    @OneToOne
    private CuponCliente cuponCliente;
    @ManyToOne
    private Cliente cliente;

}
