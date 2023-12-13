package Modelo;

public class Cuyo {

    int Id_cuyo;
    String Nombre_Cuyo;
    int Edad_Cuyo;
    String Especificaciones_Cuyo;

    public Cuyo(int id_cuyo, String nombre_Cuyo, int edad_Cuyo, String especificaciones_Cuyo) {
        Id_cuyo = id_cuyo;
        Nombre_Cuyo = nombre_Cuyo;
        Edad_Cuyo = edad_Cuyo;
        Especificaciones_Cuyo = especificaciones_Cuyo;
    }

    public int getEdad_Cuyo() {
        return Edad_Cuyo;
    }

    public int getId_cuyo() {
        return Id_cuyo;
    }

    public String getNombre_Cuyo() {
        return Nombre_Cuyo;
    }

    public String getEspecificaciones_Cuyo() {
        return Especificaciones_Cuyo;
    }
}
