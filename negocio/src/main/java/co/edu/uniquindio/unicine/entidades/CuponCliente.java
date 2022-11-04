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
public class CuponCliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private Boolean estado;
    @ManyToOne
    @JsonIgnore
    private Cupon cupon;
    @ManyToOne
    @JsonIgnore
    private Cliente cliente;

}
