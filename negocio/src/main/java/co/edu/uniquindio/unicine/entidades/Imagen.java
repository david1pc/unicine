package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(length = 1000)
    private String nombre;
    @Column(length = 1000)
    private String imagenUrl;
    @Column(length = 1000)
    private String imagenId;

    public Imagen(String nombre, String imagenUrl, String imagenId) {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.imagenId = imagenId;
    }
}
