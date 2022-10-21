package co.edu.uniquindio.unicine.dto;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.CuponCliente;
import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.MedioPago;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
@ToString
public class CompraDTO {
    private MedioPago medioPago;
    private LocalDate fecha_compra;
    private Double valor_total;
    private Funcion funcion;
    private Cliente cliente;
    private CuponCliente cuponCliente;


}
