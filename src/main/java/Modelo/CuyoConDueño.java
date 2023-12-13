package Modelo;

public class CuyoConDueño {
    private Cuyo cuyo;
    private DueñoCuyo dueñoCuyo;

    public CuyoConDueño(Cuyo cuyo, DueñoCuyo dueñoCuyo) {
        this.cuyo = cuyo;
        this.dueñoCuyo = dueñoCuyo;
    }

    // Métodos get para cada propiedad
    public String getNombreCuyo() {
        return cuyo.getNombre_Cuyo();
    }

    public String getEspecificacionesCuyo() {
        return cuyo.getEspecificaciones_Cuyo();
    }

    public String getNombreCliente() {
        return dueñoCuyo.getNombre_cliente();
    }

    public String getCelularCliente() {
        return dueñoCuyo.getCelular_cliente();
    }

    public String getCorreo_cliente() {
        return dueñoCuyo.getCorreo_cliente();
    }

    public String getDireccion() {
        return dueñoCuyo.getDireccion();
    }
}
