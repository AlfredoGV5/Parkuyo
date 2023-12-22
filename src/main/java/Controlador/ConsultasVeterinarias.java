package Controlador;

import BD.ConnectionBD;
import Modelo.Veterinario;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ConsultasVeterinarias {
    @FXML
    private ComboBox<Veterinario> Atencion_Veterinario;
    @FXML
    private TextField Atencion_Diagnostico;

    @FXML
    private TextArea Atencion_Tratamiento;

    public int idDetalleAlojamiento;
    public void setIdDetalleAlojamiento(int idDetalleAlojamiento) {
        this.idDetalleAlojamiento = idDetalleAlojamiento;
    }

    public int getIdDetalleAlojamiento() {
        return idDetalleAlojamiento;
    }

    @FXML
    void agregarAtencionV_btn() {
// Obtener los valores de los campos
        String tratamiento = Atencion_Tratamiento.getText();
        String diagnostico = Atencion_Diagnostico.getText();
        Veterinario veterinarioSeleccionado = Atencion_Veterinario.getValue();
        int idVeterinarioSeleccionado = veterinarioSeleccionado.getId_veterinario();
        System.out.println("xd"+idDetalleAlojamiento);
        System.out.println("xd"+idVeterinarioSeleccionado);

        try {
            Connection connect = ConnectionBD.getConexion();
            connect.setAutoCommit(false);

            // Insertar en la tabla Atencion_Veterinaria
            String sqlAtencionV = "INSERT INTO Atencion_Veterinaria (id_Veterinario,id_detalle_Alojamiento) VALUES (?,?)";
            PreparedStatement prepareAtencionV = connect.prepareStatement(sqlAtencionV, Statement.RETURN_GENERATED_KEYS);
            prepareAtencionV.setInt(1, idVeterinarioSeleccionado);
            prepareAtencionV.setInt(2, idDetalleAlojamiento);

            int rowsAffected = prepareAtencionV.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = prepareAtencionV.getGeneratedKeys();
                int idAtencionV = -1;

                if (generatedKeys.next()) {
                    idAtencionV = generatedKeys.getInt(1);
                }

                // Insertar en la tabla DetalleAtencion_Veterinaria
                String sqlDetalleAtencionV = "INSERT INTO DetalleAtencion_Veterinaria (id_AtencionV, tratamiento, diagnostico, fecha_Atencion) VALUES (?, ?, ?, ?)";
                PreparedStatement prepareDetalleAtencionV = connect.prepareStatement(sqlDetalleAtencionV);
                prepareDetalleAtencionV.setInt(1, idAtencionV);
                prepareDetalleAtencionV.setString(2, tratamiento);
                prepareDetalleAtencionV.setString(3, diagnostico);
                prepareDetalleAtencionV.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // Fecha actual

                int rowsAffectedDetalle = prepareDetalleAtencionV.executeUpdate();

                if (rowsAffectedDetalle > 0) {
                    connect.commit();
                    System.out.println("Consulta médica insertada con éxito.");
                } else {
                    connect.rollback();
                    System.out.println("Error al insertar en DetalleAtencion_Veterinaria.");
                }
            } else {
                connect.rollback();
                System.out.println("Error al insertar en Atencion_Veterinaria.");
            }

            connect.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargarVeterinarios() {
        Connection con = ConnectionBD.getConexion();
        String query = "SELECT id_veterinario, nombre FROM Veterinarios";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Veterinario veterinario = new Veterinario(rs.getInt("id_veterinario"), rs.getString("nombre"));
                Atencion_Veterinario.getItems().add(veterinario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        cargarVeterinarios();
    }

}


