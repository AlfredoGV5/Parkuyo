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
    private String nombreActual;
    private LocalDate fechaNacActual;
    private int id_empleado;

    @FXML
    private Button addEmployee_AddBtn;

    @FXML
    private Button addEmployee_ClearBtn;

    @FXML
    private Button addEmployee_DeleteBtn;

    @FXML
    private DatePicker addEmployee_FechaNac;

    @FXML
    private ComboBox<String> addEmployee_Genero;

    @FXML
    private TextField addEmployee_Nombre_Empleado;

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

    @FXML
    private Button addHotelBtn;

    @FXML
    private TextField addTelefono_Veterinario;

    @FXML
    private Button addVeterinarioBtn;

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
    private ComboBox<?> addVeterinario_Genero;

    @FXML
    private TextField addVeterinario_Nombre;

    @FXML
    private Button addVeterinario_UpdateBtn;

    @FXML
    private TableColumn<?, ?> addVeterinario_col_Cedula;

    @FXML
    private TableColumn<?, ?> addVeterinario_col_Direccion;

    @FXML
    private TableColumn<?, ?> addVeterinario_col_FechaN;

    @FXML
    private TableColumn<?, ?> addVeterinario_col_Nombre;

    @FXML
    private TableColumn<?, ?> addVeterinario_col_Sexo;

    @FXML
    private TableColumn<?, ?> addVeterinario_col_Sueldo;

    @FXML
    private TableColumn<?, ?> addVeterinario_col_Telefono;

    @FXML
    private TextField addVeterinario_search;

    @FXML
    private TextField addVeterinario_sueldo;

    @FXML
    private TableView<?> addVeterinario_tableView;

    @FXML
    private TableColumn<Empleado, Integer> addEmployee_col_Id;


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
                addVeterinario_Form.setVisible(false);
            } else if (clickedButton == employee_btn) {
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(true);
                adCuyo_Form.setVisible(false);
                addVeterinario_Form.setVisible(false);
            } else if (clickedButton == addCuyo_btn) {
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(false);
                adCuyo_Form.setVisible(true);
                addVeterinario_Form.setVisible(false);
            }else if(clickedButton==addVeterinarioBtn){
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(false);
                adCuyo_Form.setVisible(false);
                addVeterinario_Form.setVisible(true);
            }else if(clickedButton==addHotelBtn){
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(false);
                adCuyo_Form.setVisible(false);
                addVeterinario_Form.setVisible(false);
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
                empleadoD = new Empleado(
                        resultado.getInt("id_empleado"),
                        resultado.getString("Nombre"),
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
        addEmployee_col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
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
        addEmployee_Nombre_Empleado.setText(String.valueOf(empleadoD.getId()));
        addEmployee_Nombre_Empleado.setText(String.valueOf(empleadoD.getNombre()));
        addEmployee_Telefono.setText(String.valueOf(empleadoD.getTelefono()));
        addEmployee_sueldo.setText(String.valueOf(empleadoD.getSueldo()));
        addEmployee_Direccion_Empleado.setText(String.valueOf(empleadoD.getDireccion()));
        addEmployee_FechaNac.setValue(empleadoD.getFecha_nacimiento());
        if(empleadoD.getSexo().equals("M")) addEmployee_Genero.setValue("Masculino");
        if(empleadoD.getSexo().equals("F")) addEmployee_Genero.setValue("Femenino");



        nombreActual = empleadoD.getNombre();
        fechaNacActual = empleadoD.getFecha_nacimiento();
        id_empleado=empleadoD.getId();
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
    public void addEmployeeUpdate() throws SQLException {
        String callProcedureSQLUpdate = "{CALL actualizarEmpleado(?,?,?,?,?,?,?,?)}";
        connect = ConnectionBD.getConexion();

        try {
            connect.setAutoCommit(false);  // Deshabilitar la confirmación automática

            // Utiliza los valores originales guardados
            PreparedStatement prepare = connect.prepareStatement(callProcedureSQLUpdate);
            prepare.setString(1, nombreActual);
            prepare.setDate(2, java.sql.Date.valueOf(fechaNacActual));
            prepare.setString(3, addEmployee_Nombre_Empleado.getText());
            prepare.setString(4, addEmployee_Telefono.getText());
            prepare.setString(5, addEmployee_Direccion_Empleado.getText());
            prepare.setDouble(6, Double.parseDouble(addEmployee_sueldo.getText()));
            prepare.setString(7, (String) addEmployee_Genero.getSelectionModel().getSelectedItem());
            LocalDate fechaNacimiento = addEmployee_FechaNac.getValue();
            java.sql.Date fechaNacimientoSQL = java.sql.Date.valueOf(fechaNacimiento);
            prepare.setDate(8, fechaNacimientoSQL);

            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                connect.commit();  // Confirmar la transacción si hay filas afectadas

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información Operación");
                alert.setHeaderText(null);
                alert.setContentText("Actualizado Correctamente");
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

    public void addEmployeeDelete() throws SQLException {
        String sqlBorrar= "{CALL borrarEmpleado(?)}";
        connect = ConnectionBD.getConexion();
        try{
            connect.setAutoCommit(false);  // Deshabilitar la confirmación automática

            // Utiliza los valores originales guardados
            PreparedStatement prepare = connect.prepareStatement(sqlBorrar);
            prepare.setInt(1,id_empleado);

            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                connect.commit();  // Confirmar la transacción si hay filas afectadas

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información Operación");
                alert.setHeaderText(null);
                alert.setContentText("Borrado Correctamente");
                alert.showAndWait();

                addEmployeeReset();
                addEmpleadoShowListData();
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEmpleadoShowListData();
    }
}
