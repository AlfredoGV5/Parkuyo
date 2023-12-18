package Controlador;

import BD.ConnectionBD;
import Modelo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class VeterinarioController implements Initializable {

    @FXML
    private TextField addTelefono_Veterinario;

    @FXML
    private Button addVeterinario_AddBtn;

    @FXML
    private TextField addVeterinario_Cedula;

    @FXML
    private Button addVeterinario_ClearBtn;

    @FXML
    private Button addVeterinario_DeleteBtn;

    @FXML
    private TextField addVeterinario_Direccion;

    @FXML
    private DatePicker addVeterinario_FechaNac;

    @FXML
    private AnchorPane addVeterinario_Form;

    @FXML
    private ComboBox<String> addVeterinario_Genero;

    @FXML
    private TextField addVeterinario_Nombre;

    @FXML
    private TableColumn<Veterinario, String> addVeterinario_col_Cedula;

    @FXML
    private TableColumn<Veterinario, String> addVeterinario_col_Direccion;

    @FXML
    private TableColumn<Veterinario, Date> addVeterinario_col_FechaN;

    @FXML
    private TableColumn<Veterinario, String>addVeterinario_col_Nombre;

    @FXML
    private TableColumn<Veterinario, String> addVeterinario_col_Sexo;

    @FXML
    private TableColumn<Veterinario, Float> addVeterinario_col_Sueldo;

    @FXML
    private TableColumn<Veterinario, String> addVeterinario_col_Telefono;

    @FXML
    private TableColumn<Veterinario, Integer>addVeterinario_col_Id;

    @FXML
    private TextField addVeterinario_search;

    @FXML
    private TextField addVeterinario_sueldo;

    @FXML
    private TableView<Veterinario> addVeterinario_tableView;

    private Connection connect;

    int id_Veterinario;


    public ObservableList<Veterinario> addVeterinarioListData() {
        String sql = "SELECT * FROM Veterinarios";
        ObservableList<Veterinario> listData = FXCollections.observableArrayList();
        connect = ConnectionBD.getConexion();

        try (PreparedStatement preparedStatement = connect.prepareStatement(sql);
             ResultSet resultado = preparedStatement.executeQuery()) {

            while (resultado.next()) {
                Veterinario veterinario = new Veterinario(
                        resultado.getInt("id_veterinario"),
                        resultado.getString("nombre"),
                        resultado.getString("telefono"),
                        resultado.getString("direccion"),
                        resultado.getFloat("sueldo"),
                        resultado.getString("sexo"),
                        resultado.getString("cedula_profesional"),
                        resultado.getDate("fecha_nacimiento")
                );
                listData.add(veterinario); // Añadir el veterinario a la lista
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void addVeterinarioShowListData() {
        System.out.println(addVeterinarioListData());
        ObservableList<Veterinario> list = addVeterinarioListData();
        addVeterinario_col_Id.setCellValueFactory(new PropertyValueFactory<>("id_veterinario"));
        addVeterinario_col_Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        addVeterinario_col_Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        addVeterinario_col_Direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        addVeterinario_col_Sueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));
        addVeterinario_col_Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        addVeterinario_col_Cedula.setCellValueFactory(new PropertyValueFactory<>("cedula_profesional"));
        addVeterinario_col_FechaN.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        addVeterinario_tableView.setItems(list);
    }

    public void addVeterinario() throws SQLException {
        connect = ConnectionBD.getConexion();
        String callProcedureSQL = "{CALL InsertarVeterinario(?,?,?,?,?,?,?)}";
        try {
            connect.setAutoCommit(false);  // Deshabilitar la confirmación automático
            PreparedStatement prepare = connect.prepareStatement(callProcedureSQL);
            prepare.setString(1, addVeterinario_Nombre.getText());
            prepare.setString(2, addTelefono_Veterinario.getText());
            prepare.setString(3, addVeterinario_Direccion.getText());
            prepare.setFloat(4, Float.parseFloat(addVeterinario_sueldo.getText()));
            LocalDate fechaNacimiento = addVeterinario_FechaNac.getValue();
            java.sql.Date fechaNacimientoSQL = java.sql.Date.valueOf(fechaNacimiento);
            prepare.setDate(5, fechaNacimientoSQL);
            prepare.setString(6, addVeterinario_Genero.getSelectionModel().getSelectedItem());
            prepare.setString(7, addVeterinario_Cedula.getText());
            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                connect.commit();  // Confirmar la transacción si hay filas afectadas
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información Operación");
                alert.setHeaderText(null);
                alert.setContentText("Agregado Correctamente");
                alert.showAndWait();
                addVeterinarioReset();
                addVeterinarioShowListData();


            } else {
                connect.rollback();  // Hacer rollback si no hay filas afectadas
                System.out.println("No se pudo agregar el empleado.");
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
    public void addVeterinarioReset(){
        addVeterinario_Nombre.setText("");
        addVeterinario_Direccion.setText("");
        addTelefono_Veterinario.setText("");
        addVeterinario_sueldo.setText("");
        addVeterinario_FechaNac.setValue(null);
        addVeterinario_Genero.getSelectionModel().clearSelection();
        addVeterinario_Cedula.setText("");
    }

    public void addVeterinarioSelect() {
        Veterinario veterinario = addVeterinario_tableView.getSelectionModel().getSelectedItem();
        int num = addVeterinario_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        addVeterinario_Nombre.setText(String.valueOf(veterinario.getNombre()));
        addTelefono_Veterinario.setText(String.valueOf(veterinario.getTelefono()));
        addVeterinario_sueldo.setText(String.valueOf(veterinario.getSueldo()));
        addVeterinario_Direccion.setText(String.valueOf(veterinario.getDireccion()));
        addVeterinario_FechaNac.setValue(veterinario.getFecha_nacimiento());
        if(veterinario.getSexo().equals("Ma")) addVeterinario_Genero.setValue("Masculino");
        if(veterinario.getSexo().equals("Fe")) addVeterinario_Genero.setValue("Femenino");
        addVeterinario_Cedula.setText(String.valueOf(veterinario.getCedula_profesional()));
        id_Veterinario=veterinario.getId_veterinario();
    }

    public void addVeterinarioDelete() throws SQLException {
        String sqlBorrar= "{CALL borrarVeterinario(?)}";
        connect = ConnectionBD.getConexion();
        try{
            connect.setAutoCommit(false);  // Deshabilitar la confirmación automática

            // Utiliza los valores originales guardados
            PreparedStatement prepare = connect.prepareStatement(sqlBorrar);
            prepare.setInt(1,id_Veterinario);

            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                connect.commit();  // Confirmar la transacción si hay filas afectadas

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información Operación");
                alert.setHeaderText(null);
                alert.setContentText("Borrado Correctamente");
                alert.showAndWait();

                addVeterinarioReset();
                addVeterinarioShowListData();
            } else {
                connect.rollback();  // Hacer rollback si no hay filas afectadas
                System.out.println("No se pudo Borrar el empleado.");
            }
        } catch(Exception e) {
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


    public void addVeterinarioUpdate() throws SQLException {
        String callProcedureSQLUpdate = "{CALL ActualizarVeterinario(?,?,?,?,?,?,?,?)}";
        connect = ConnectionBD.getConexion();

        try {
            connect.setAutoCommit(false);  // Deshabilitar la confirmación automática

            // Utiliza los valores originales guardados
            PreparedStatement prepare = connect.prepareStatement(callProcedureSQLUpdate);
            prepare.setInt(1, id_Veterinario);
            prepare.setString(2, addVeterinario_Nombre.getText());
            prepare.setString(3, addTelefono_Veterinario.getText());
            prepare.setString(4, addVeterinario_Direccion.getText());
            prepare.setFloat(5, Float.parseFloat(addVeterinario_sueldo.getText()));
            LocalDate fechaNacimiento = addVeterinario_FechaNac.getValue();
            java.sql.Date fechaNacimientoSQL = java.sql.Date.valueOf(fechaNacimiento);
            prepare.setDate(6,fechaNacimientoSQL);
            prepare.setString(7, addVeterinario_Genero.getSelectionModel().getSelectedItem());
            prepare.setString(8, addVeterinario_Cedula.getText());

            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                connect.commit();  // Confirmar la transacción si hay filas afectadas

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información Operación");
                alert.setHeaderText(null);
                alert.setContentText("Actualizado Correctamente");
                alert.showAndWait();

                addVeterinarioReset();
                addVeterinarioShowListData();
            } else {
                connect.rollback();  // Hacer rollback si no hay filas afectadas
                System.out.println("No se pudo agregar el empleado.");
            }
        } catch(Exception e) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addVeterinarioShowListData();
        addVeterinarioListData();
    }
}
