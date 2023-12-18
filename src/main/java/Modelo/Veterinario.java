package Modelo;

import java.time.LocalDate;
import java.util.Date;

public class Veterinario {
    private int id_veterinario;
    private String nombre;
    private String telefono;
    private String direccion;
    private float sueldo;
    private String  sexo;
    private String cedula_profesional;
    private Date fecha_nacimiento;


    public Veterinario(int id_veterinario, String nombre, String telefono, String direccion, float sueldo, String sexo, String cedula_profesional, Date fecha_nacimiento) {
        this.id_veterinario=id_veterinario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.sueldo = sueldo;
        this.sexo = sexo;
        this.cedula_profesional = cedula_profesional;
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

    public String  getSexo() {
        return sexo;
    }

    public String getCedula_profesional() {return cedula_profesional;}

    public int getId_veterinario() {
        return id_veterinario;
    }
    public LocalDate getFecha_nacimiento() {
        return ((java.sql.Date) fecha_nacimiento).toLocalDate();
    }

}
