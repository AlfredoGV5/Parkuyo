package Modelo;

import java.util.Date;

public class Veterinario {
    private String nombre;
    private String telefono;
    private String direccion;
    private float sueldo;
    private char sexo;
    private String cedula;
    private Date fecha_nacimiento;


    public Veterinario(String nombre, String telefono, String direccion, float sueldo, char sexo, String cedula, Date fecha_nacimiento) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.sueldo = sueldo;
        this.sexo = sexo;
        this.cedula = cedula;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public float getSueldo() {
        return sueldo;
    }

    public char getSexo() {
        return sexo;
    }

    public String getCedula() {
        return cedula;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }
}
