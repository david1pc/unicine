package co.edu.uniquindio.unicine.entidades;

import java.time.LocalDate;
import java.util.List;

public class NuevaCompra {
    private Integer codigo;
    private String medioPago;
    private LocalDate fecha_compra;
    private Double valor_total;
    private Funcion funcion;
    private List<Entrada> entradas;
    private List<CompraConfiteria>compraConfiterias;
    private List<CompraCombo>compraCombos;
    private CuponCliente cuponCliente;
    private String username;
    private String contenido;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public LocalDate getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(LocalDate fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public Double getValor_total() {
        return valor_total;
    }

    public void setValor_total(Double valor_total) {
        this.valor_total = valor_total;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public List<CompraConfiteria> getCompraConfiterias() {
        return compraConfiterias;
    }

    public void setCompraConfiterias(List<CompraConfiteria> compraConfiterias) {
        this.compraConfiterias = compraConfiterias;
    }

    public List<CompraCombo> getCompraCombos() {
        return compraCombos;
    }

    public void setCompraCombos(List<CompraCombo> compraCombos) {
        this.compraCombos = compraCombos;
    }

    public CuponCliente getCuponCliente() {
        return cuponCliente;
    }

    public void setCuponCliente(CuponCliente cuponCliente) {
        this.cuponCliente = cuponCliente;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "NuevaCompra{" +
                "codigo=" + codigo +
                ", medioPago='" + medioPago + '\'' +
                ", fecha_compra=" + fecha_compra +
                ", valor_total=" + valor_total +
                ", funcion=" + funcion +
                ", entradas=" + entradas +
                ", compraConfiterias=" + compraConfiterias +
                ", compraCombos=" + compraCombos +
                ", cuponCliente=" + cuponCliente +
                ", username='" + username + '\'' +
                ", contenido='" + contenido + '\'' +
                '}';
    }
}
