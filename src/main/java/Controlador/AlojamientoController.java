package Controlador;

import BD.ConnectionBD;
import Modelo.Cuyo;
import Modelo.CuyoConDueño;
import Modelo.DueñoCuyo;
import Modelo.Veterinario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlojamientoController {
    private Connection connect;
    @FXML
    private Button AlojamientoAgregar_btn;

    @FXML
    private Button AlojamientoEditar_btn;

    @FXML
    private Button AtencionVAgregar_btn;

    @FXML
    private TextField Atencion_Diagnostico;

    @FXML
    private TextArea Atencion_Tratamiento;

    @FXML
    private ComboBox<Veterinario> Atencion_Veterinario;

    @FXML
    private TextField Costo_Alojamiento;

    @FXML
    private TextField Cuarto_Alojamiento;

    @FXML
    private ComboBox<?> Empleado_Alojamiento;

    @FXML
    private DatePicker Fecha_SalidaAlojamiento;

    @FXML
    private HBox tarjetasContainer; // VBox definido en el FXML

    @FXML
    public void initialize() {
        cargarDatos();
        cargarVeterinarios();
    }
    private void cargarDatos() {
        ObservableList<CuyoConDueño> lista = addCuyoConDueñoListData();
        System.out.println("SI ENTRE ACAAAA");
        System.out.println(lista);
        for (CuyoConDueño cuyoConDueño : lista) {
            AnchorPane tarjeta = crearTarjeta(cuyoConDueño);
            tarjetasContainer.getChildren().add(tarjeta);
        }
    }
    private AnchorPane crearTarjeta(CuyoConDueño cuyoConDueño) {
        AnchorPane tarjeta = new AnchorPane();
        tarjeta.setStyle("-fx-border-color: black; -fx-background-color: white;");
        tarjeta.setMinHeight(100);
        tarjeta.setMinWidth(300);

        Label nombreCuyo = new Label("Nombre Cuyo: " + cuyoConDueño.getNombreCuyo());
        nombreCuyo.setLayoutX(10);
        nombreCuyo.setLayoutY(10);

        Label edad = new Label("Edad: " + cuyoConDueño.getEspecificacionesCuyo());
        edad.setLayoutX(10);
        edad.setLayoutY(35);

        Label especificaciones = new Label("Especificaciones: " + cuyoConDueño.getNombreCliente());
        especificaciones.setLayoutX(10);
        especificaciones.setLayoutY(60);

        Label nombreCliente = new Label("Nombre Cliente: " + cuyoConDueño.getNombreCliente());
        nombreCliente.setLayoutX(10);
        nombreCliente.setLayoutY(85);

        Label celularCliente = new Label("Celular Cliente: " + cuyoConDueño.getCelularCliente());
        celularCliente.setLayoutX(10);
        celularCliente.setLayoutY(110);

        Label correoCliente = new Label("Correo Cliente: " + cuyoConDueño.getCorreo_cliente());
        correoCliente.setLayoutX(10);
        correoCliente.setLayoutY(135);

        Label direccionCliente = new Label("Dirección Cliente: " + cuyoConDueño.getDireccion());
        direccionCliente.setLayoutX(10);
        direccionCliente.setLayoutY(160);

        // Agregar todos los labels a la tarjeta
        tarjeta.getChildren().addAll(nombreCuyo, edad, especificaciones, nombreCliente, celularCliente, correoCliente, direccionCliente);

        return tarjeta;
    }
    private ObservableList<CuyoConDueño> addCuyoConDueñoListData() {
        String sql = "{CALL ObtenerCuyosYClientes()}";
        ObservableList<CuyoConDueño> listData = FXCollections.observableArrayList();
        connect = ConnectionBD.getConexion();

        try (PreparedStatement preparedStatement = connect.prepareStatement(sql);) {
            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado.next()) {
                Cuyo cuyo = new Cuyo(
                        resultado.getInt("id_cuyo"),
                        resultado.getString("nombre"),
                        resultado.getInt("edad"),
                        resultado.getString("especificaciones")
                );
                DueñoCuyo dueñoCuyo = new DueñoCuyo(
                        resultado.getString("nombre_cliente"),
                        resultado.getString("celular_cliente"),
                        resultado.getString("correo_cliente"),
                        resultado.getString("direccion_cliente")
                );

                listData.add(new CuyoConDueño(cuyo, dueñoCuyo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
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

}
