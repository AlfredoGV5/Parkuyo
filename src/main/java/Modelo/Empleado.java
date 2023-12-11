package Modelo;

import java.util.Date;

public class Empleado {
    private String nombre;
    private String telefono;
    private String direccion;
    private float sueldo;
    private String sexo;
    private Date fecha_nacimiento;

    public Empleado(String nombre, String telefono, String direccion, float sueldo, String sexo, Date fecha_nacimiento) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.sueldo = sueldo;
        this.sexo = sexo;
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

    public String getSexo() {
        return sexo;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }
}
