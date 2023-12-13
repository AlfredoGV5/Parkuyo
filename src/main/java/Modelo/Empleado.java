package Modelo;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Date;

public class Empleado {

    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private float sueldo;
    private String sexo;
    private LocalDate fecha_nacimiento;

    public Empleado(int id,String nombre, String telefono, String direccion, float sueldo, String sexo, LocalDate fecha_nacimiento) {
        this.id=id;
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
    public int getId() {
        return id;
    }

    public LocalDate getFecha_nacimiento() {return fecha_nacimiento;}
}
