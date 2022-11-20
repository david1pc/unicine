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
public class DistribucionSillas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    @Column(length = 1000)
    private String esquema;
    private Integer total_sillas;
    private Integer filas;
    private Integer columnas;
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "distribucionSillas")
    private List<Sala>salas;
}
