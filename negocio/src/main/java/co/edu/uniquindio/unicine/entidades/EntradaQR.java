package co.edu.uniquindio.unicine.entidades;

public class EntradaQR {
    private Integer codigo;
    private Double precio;
    private Character fila;
    private Integer columna;
    private String ind;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Character getFila() {
        return fila;
    }

    public void setFila(Character fila) {
        this.fila = fila;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }
}
