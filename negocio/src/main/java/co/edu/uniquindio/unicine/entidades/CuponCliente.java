package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class CuponCliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private String estado;
    @OneToOne(mappedBy = "cuponCliente")
    private Compra compra;
    @ManyToOne
    private Cupon cupon;
    @ManyToOne
    private Cliente cliente;

}
