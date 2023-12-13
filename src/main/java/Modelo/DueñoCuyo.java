package Modelo;

public class DueñoCuyo {
    String nombre_cliente;
    String celular_cliente;
    String correo_cliente;
    String direccion;

    public DueñoCuyo(String nombre_cliente, String celular_cliente, String correo_cliente, String direccion) {
        this.nombre_cliente = nombre_cliente;
        this.celular_cliente = celular_cliente;
        this.correo_cliente = correo_cliente;
        this.direccion = direccion;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public String getCelular_cliente() {
        return celular_cliente;
    }

    public String getCorreo_cliente() {
        return correo_cliente;
    }

    public String getDireccion() {
        return direccion;
    }
}