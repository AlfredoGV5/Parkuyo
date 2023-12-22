package Controlador;

import BD.ConnectionBD;
import Modelo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;


public class AlojamientoController {
    private Connection connect;

    @FXML
    private AnchorPane TarjetaCuyo;


    @FXML
    private TextField Atencion_Diagnostico;

    @FXML
    private TextArea Atencion_Tratamiento;

    @FXML
    private ComboBox<Veterinario> Atencion_Veterinario;
    @FXML
    private DatePicker Fecha_EntradaAlojamiento;

    @FXML
    private TextField Cuarto_Alojamiento;

    @FXML
    private ComboBox<Empleado> Empleado_Alojamiento;

    @FXML
    private DatePicker Fecha_SalidaAlojamiento;
    @FXML
    private Label Cuyo_Descripcion;

    @FXML
    private HBox tarjetasContainer; // VBox definido en el FXML


    int id_clienteSeleccionado=0;
    int id_veterinarioSeleccionado=0;

    @FXML
    public void initialize() throws SQLException {
        cargarDatos();
        cargarVeterinarios();
        cargarEmpleados();
    }
    private void cargarDatos() throws SQLException {
        ObservableList<CuyoConDueño> lista = addCuyoConDueñoListData();
        for (CuyoConDueño cuyoConDueño : lista) {
            AnchorPane tarjeta = crearTarjeta(cuyoConDueño);
            tarjeta.setOnMouseClicked(this::TargetSelected);
            tarjetasContainer.getChildren().add(tarjeta);

        }
    }

    private AnchorPane crearTarjeta(CuyoConDueño cuyoConDueño) throws SQLException {
        AnchorPane tarjeta = new AnchorPane();
        tarjeta.setStyle("-fx-border-color: #ffffff; -fx-background-color: linear-gradient(to bottom right, #00b4fc, #17f9ff)");
        tarjeta.setMinHeight(100);
        tarjeta.setMinWidth(300);

        Label nombreCuyo = new Label("Nombre Cuyo: " + cuyoConDueño.getNombreCuyo());
        nombreCuyo.setLayoutX(10);
        nombreCuyo.setLayoutY(10);

        Label edad = new Label("Edad: " + cuyoConDueño.getEspecificacionesCuyo());
        edad.setLayoutX(10);
        edad.setLayoutY(35);

        Label nombreCliente = new Label("Nombre Cliente: " + cuyoConDueño.getNombreCliente());
        nombreCliente.setLayoutX(10);
        nombreCliente.setLayoutY(85);

        Label celularCliente = new Label("Celular Cliente: " + cuyoConDueño.getCelularCliente());
        celularCliente.setLayoutX(10);
        celularCliente.setLayoutY(110);

        Label correoCliente = new Label("Correo Cliente: " + cuyoConDueño.getCorreo_cliente());
        correoCliente.setLayoutX(10);
        correoCliente.setLayoutY(135);
        // Agregar todos los labels a la tarjeta
        tarjeta.getChildren().addAll(nombreCuyo, edad, nombreCliente, celularCliente, correoCliente);
        tarjeta.setUserData(cuyoConDueño);
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
                        resultado.getInt("id_cliente"),
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

    public void cargarEmpleados() {
        Connection con = ConnectionBD.getConexion();
        String query = "SELECT id_empleado, nombre FROM empleados";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Empleado empleado = new Empleado(rs.getInt("id_empleado"), rs.getString("nombre"));
                Empleado_Alojamiento.getItems().add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void TargetSelected(MouseEvent event) {
        AnchorPane tarjetaSeleccionada = (AnchorPane) event.getSource();
        CuyoConDueño cuyoSeleccionado = (CuyoConDueño) tarjetaSeleccionada.getUserData();
        if (cuyoSeleccionado != null) {
            Cuyo_Descripcion.setText(cuyoSeleccionado.getNombreCuyo());
            id_clienteSeleccionado=cuyoSeleccionado.getIdCliente();
        }
    }

    @FXML
    void agregarAlojamiento_btn() throws SQLException {
        connect = ConnectionBD.getConexion();
        int id_AlojamientoGenerado=0;
        String query="INSERT INTO Alojamiento (id_cliente,fecha_ingreso,fecha_egreso,costo) VALUES(?,?,?,?) SELECT SCOPE_IDENTITY() AS IdGenerado;";
        try {
            connect.setAutoCommit(false);  // Deshabilitar la confirmación automático
            PreparedStatement prepare = connect.prepareStatement(query);
            prepare.setInt(1, id_clienteSeleccionado);
            prepare.setDate(2, java.sql.Date.valueOf(Fecha_EntradaAlojamiento.getValue()));
            prepare.setDate(3,java.sql.Date.valueOf(Fecha_SalidaAlojamiento.getValue()));
            long dias = ChronoUnit.DAYS.between(Fecha_EntradaAlojamiento.getValue(), Fecha_SalidaAlojamiento.getValue());

            prepare.setFloat(4,dias*400);
            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                connect.commit();  // Confirmar la transacción si hay filas afectadas
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información Operación");
                alert.setHeaderText(null);
                alert.setContentText("Se agrego Correctamente");
                alert.showAndWait();

                try (ResultSet rs = prepare.getGeneratedKeys()) {
                    if (rs.next()) {
                        id_AlojamientoGenerado=(rs.getInt("IdGenerado"));
                    }
                }
                /*Llamamos el procedure para insertar al dueño*/
                String sqlDetalle = "INSERT INTO detalle_Alojamiento(id_ALojamiento,id_empleado,no_cuarto) VALUES (?,?,?)";
                try {
                    connect.setAutoCommit(false);  // Deshabilitar la confirmación automático
                    PreparedStatement prepare2 = connect.prepareStatement(sqlDetalle);
                    prepare2.setInt(1, id_AlojamientoGenerado);
                    Empleado empleadoSeleccionado = Empleado_Alojamiento.getSelectionModel().getSelectedItem();
                    System.out.println("El empleado fue "+empleadoSeleccionado.getId());
                    prepare2.setInt(2, empleadoSeleccionado.getId());
                    prepare2.setInt(3, Integer.parseInt(Cuarto_Alojamiento.getText()));
                    int rowsAffected2 = prepare2.executeUpdate();
                    if (rowsAffected2 > 0) {
                        connect.commit();  // Confirmar la transacción si hay filas afectadas
                    }
                } catch (Exception e) {e.printStackTrace();}

            } else {
                connect.rollback();  // Hacer rollback si no hay filas afectadas
            }
        } catch (Exception e) {
            connect.rollback();  // Hacer rollback en caso de error
            System.out.println(e);
            e.printStackTrace();
        } finally {
            try {
                connect.setAutoCommit(true);  // Restaurar la confirmación automática
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}