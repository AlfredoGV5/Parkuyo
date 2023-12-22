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
import java.sql.*;
import java.time.temporal.ChronoUnit;


public class AlojamientoController {
    int idDetalleAlojamiento;
    private Connection connect;

    @FXML
    private AnchorPane TarjetaCuyo;


    @FXML
    private TextField Atencion_Diagnostico;

    @FXML
    private TextArea Atencion_Tratamiento;

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
    @FXML
    public void initialize() throws SQLException {
        cargarDatos();
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
            idDetalleAlojamiento= obtenerIdDetalleAlojamiento(id_clienteSeleccionado);
            System.out.println("SIII, EL ID ES"+idDetalleAlojamiento);

        }
    }

    private int obtenerIdDetalleAlojamiento(int idCliente) {
        int idDetalleAlojamiento = -1; // Valor predeterminado si no se encuentra

        // Aquí realiza la consulta SQL para obtener el id_detalle_Alojamiento
        String sql = "SELECT da.id_Detalle_Alojamiento " +
                "FROM detalle_Alojamiento da " +
                "INNER JOIN Alojamiento a ON da.id_Alojamiento = a.id_Alojamiento " +
                "INNER JOIN cliente_DueñoCuyo cd ON a.id_cliente = cd.id_cliente " +
                "WHERE cd.id_cliente = ?";

        try (Connection con = ConnectionBD.getConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idCliente);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idDetalleAlojamiento = rs.getInt("id_Detalle_Alojamiento");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idDetalleAlojamiento;
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
                    PreparedStatement prepare2 = connect.prepareStatement(sqlDetalle, Statement.RETURN_GENERATED_KEYS);
                    prepare2.setInt(1, id_AlojamientoGenerado);
                    Empleado empleadoSeleccionado = Empleado_Alojamiento.getSelectionModel().getSelectedItem();
                    prepare2.setInt(2, empleadoSeleccionado.getId());
                    prepare2.setInt(3, Integer.parseInt(Cuarto_Alojamiento.getText()));
                    int rowsAffected2 = prepare2.executeUpdate();
                    if (rowsAffected2 > 0) {
                        // Obtener las claves generadas automáticamente
                        ResultSet generatedKeys = prepare2.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            idDetalleAlojamiento = generatedKeys.getInt(1); // Suponiendo que la columna se llama "id_detalle_Alojamiento"
                            System.out.println("Id_detalle_Alojamiento insertado: " + idDetalleAlojamiento);
                        }
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

    @FXML
    void abrirConsultas_btn() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/poo/parkuyo/ConsultasVeterinarias.fxml"));
        Parent root = loader.load();
        ConsultasVeterinarias consultasController = loader.getController();
        consultasController.setIdDetalleAlojamiento(idDetalleAlojamiento);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }


}