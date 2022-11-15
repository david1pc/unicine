package co.edu.uniquindio.unicine.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class Horario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private LocalTime hora;
    private String dia;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "horario")
    private List<Funcion>funciones;

    @Builder
    public Horario(Integer codigo, LocalTime hora, String dia, LocalDate fecha_inicio, LocalDate fecha_fin) {
        this.codigo = codigo;
        this.hora = hora;
        this.dia = dia;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }
}
