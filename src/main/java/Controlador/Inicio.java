package Controlador;

import BD.ConnectionBD;
import Modelo.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
public class Inicio implements Initializable {
    @FXML
    private Button addEmployee_AddBtn;

    @FXML
    private Button addEmployee_ClearBtn;

    @FXML
    private Button addEmployee_DeleteBtn;

    @FXML
    private DatePicker addEmployee_FechaNac;

    @FXML
    private ComboBox<?> addEmployee_Genero;

    @FXML
    private TextField addEmployee_Nombre_Empleado;

    @FXML
    private ComboBox<String> addEmployee_Posicion;

    @FXML
    private TextField addEmployee_Telefono;

    @FXML
    private Button addEmployee_UpdateBtn;

    @FXML
    private TableColumn<Empleado, Date> addEmployee_col_Fecha_Nacimiento;

    @FXML
    private TableColumn<Empleado, String> addEmployee_col_Nombre;

    @FXML
    private TableColumn<Empleado, String> addEmployee_col_Sexo;

    @FXML
    private TableColumn<Empleado, Float> addEmployee_col_Sueldo;

    @FXML
    private TableColumn<Empleado, String> addEmployee_col_Telefono;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private TextField addEmployee_sueldo;

    @FXML
    private TableView<Empleado> addEmployee_tableView;

    @FXML
    private Button employee_btn;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_fomr;

    @FXML
    private AnchorPane id_Dash;

    @FXML
    private BarChart<?, ?> id_Dash_Chart;

    @FXML
    private Label id_Dash_CuyosR;

    @FXML
    private Label id_Dash_TotalEmploymeent;

    @FXML
    private Label id_Dash_VtasDiarias;

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane adCuyo_Form;

    @FXML
    private Button addCuyo_btn;

    @FXML
    private TableColumn<Empleado, String> addEmployee_col_Direccion;

    @FXML
    private TextField addEmployee_Direccion_Empleado;


    private Connection connect;

    //Metodo para cerrar
    public void close() {
        System.exit(0);
    }

    /*METODO PARA CAMBIAR ENTRE LAS VENTANAS DEL PANEL*/
    public void switchForm(ActionEvent event) {
        if (event.getSource() instanceof Button) {
            Button clickedButton = (Button) event.getSource();
            if (clickedButton == home_btn) {
                id_Dash.setVisible(true);
                addEmployee_form.setVisible(false);
                adCuyo_Form.setVisible(false);
            } else if (clickedButton == employee_btn) {
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(true);
                adCuyo_Form.setVisible(false);
            } else if (clickedButton == addCuyo_btn) {
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(false);
                adCuyo_Form.setVisible(true);
            }
        }
    }

    /*Metodo para enumerar a los empleados*/
    public ObservableList<Empleado> addEmpleadoListData() {
        String sql = "SELECT * FROM empleados";
        ObservableList<Empleado> listData = FXCollections.observableArrayList();
        connect = ConnectionBD.getConexion();

        try (PreparedStatement preparedStatement = connect.prepareStatement(sql);) {
            ResultSet resultado = preparedStatement.executeQuery();
            Empleado empleadoD;
            while (resultado.next()) {
                empleadoD = new Empleado(resultado.getString("Nombre"),
                        resultado.getString("telefono"),
                        resultado.getString("direccion"),
                        resultado.getFloat("sueldo"),
                        resultado.getString("sexo"),
                        resultado.getDate("fecha_nacimiento").toLocalDate());
                listData.add(empleadoD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Empleado> addEmpleadoList;

    public void addEmpleadoShowListData() {
        addEmpleadoList = addEmpleadoListData();
        addEmployee_col_Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        addEmployee_col_Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        addEmployee_col_Direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        addEmployee_col_Sueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));
        addEmployee_col_Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        addEmployee_col_Fecha_Nacimiento.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));

        addEmployee_tableView.setItems(addEmpleadoList);
    }

    /*Esto es para cuando seleccione una fila de la tabla, los datos los mande a los TextField*/
    public void addEmployeeSelect() {
        Empleado empleadoD = addEmployee_tableView.getSelectionModel().getSelectedItem();
        int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        addEmployee_Nombre_Empleado.setText(String.valueOf(empleadoD.getNombre()));
        addEmployee_Telefono.setText(String.valueOf(empleadoD.getTelefono()));
        addEmployee_sueldo.setText(String.valueOf(empleadoD.getSueldo()));
        addEmployee_Direccion_Empleado.setText(String.valueOf(empleadoD.getDireccion()));
        addEmployee_FechaNac.setValue(empleadoD.getFecha_nacimiento());
    }

    /*Metodo para agregar Empleado o Veterinario*/
    public void AddEmployeeADD() throws SQLException {
    connect = ConnectionBD.getConexion();
        String callProcedureSQL = "{CALL InsertarEmpleado(?,?,?,?,?,?)}";
        try {
            connect.setAutoCommit(false);  // Deshabilitar la confirmación automática

            PreparedStatement prepare = connect.prepareStatement(callProcedureSQL);
            prepare.setString(1, addEmployee_Nombre_Empleado.getText());
            prepare.setString(2, addEmployee_Telefono.getText());
            prepare.setString(3, addEmployee_Direccion_Empleado.getText());
            prepare.setDouble(4, Double.parseDouble(addEmployee_sueldo.getText()));
            LocalDate fechaNacimiento = addEmployee_FechaNac.getValue();
            java.sql.Date fechaNacimientoSQL = java.sql.Date.valueOf(fechaNacimiento);
            prepare.setDate(5, fechaNacimientoSQL);
            prepare.setString(6, (String) addEmployee_Genero.getSelectionModel().getSelectedItem());

            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                connect.commit();  // Confirmar la transacción si hay filas afectadas

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información Operación");
                alert.setHeaderText(null);
                alert.setContentText("Agregado Correctamente");
                alert.showAndWait();

                addEmployeeReset();
                addEmpleadoShowListData();
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



    public void addEmployeeReset(){
        addEmployee_Nombre_Empleado.setText("");
        addEmployee_Telefono.setText("");
        addEmployee_Direccion_Empleado.setText("");
        addEmployee_sueldo.setText("");
        addEmployee_FechaNac.setValue(null);
        addEmployee_Genero.getSelectionModel().clearSelection();

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEmpleadoShowListData();
    }
}
