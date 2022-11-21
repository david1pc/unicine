package co.edu.uniquindio.unicine.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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
    @EqualsAndHashCode.Include
    private Integer codigo;
    @Enumerated(EnumType.STRING)
    private MedioPago medioPago;
    private LocalDate fecha_compra;
    private Double valor_total;
    @ManyToOne
    @JsonIgnore
    private Funcion funcion;

    @ToString.Exclude
    @OneToMany(mappedBy = "compra")
    private List<Entrada>entradas;

    @ToString.Exclude
    @OneToMany(mappedBy = "compra")
    private List<CompraConfiteria>compraConfiterias;

    @ToString.Exclude
    @OneToMany(mappedBy = "compra")
    private List<CompraCombo>compraCombos;

    @OneToOne
    @JsonIgnore
    private CuponCliente cuponCliente;
    @ManyToOne
    @JsonIgnore
    private Cliente cliente;

    public Compra(MedioPago medioPago, LocalDate fecha_compra, Double valor_total, Funcion funcion, CuponCliente cuponCliente, Cliente cliente) {
        this.medioPago = medioPago;
        this.fecha_compra = fecha_compra;
        this.valor_total = valor_total;
        this.funcion = funcion;
        this.cuponCliente = cuponCliente;
        this.cliente = cliente;
    }

    public Compra(MedioPago medioPago, LocalDate fecha_compra, Double valor_total, Funcion funcion, List<CompraCombo> compraCombos, List<CompraConfiteria> compraConfiterias, List<Entrada> entradas, Cliente cliente, CuponCliente cuponCliente) {
        this.medioPago = medioPago;
        this.fecha_compra = fecha_compra;
        this.valor_total = valor_total;
        this.funcion = funcion;
        this.cliente = cliente;
    }
}
