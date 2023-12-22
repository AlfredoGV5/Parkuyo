package Controlador;

import BD.ConnectionBD;
import Modelo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.*;
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
    private AnchorPane addAlojamiento_Form;
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
    private AnchorPane id_Dash;

    @FXML
    private Label id_Dash_CuyosR;

    @FXML
    private Label id_Dash_TotalEmploymeent;

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
    private Button addVeterinarioBtn;


    @FXML
    private AnchorPane addVeterinario_Form;

    @FXML
    private TableColumn<Empleado, Integer> addEmployee_col_Id;

    @FXML
    private TextField adCuyo_Form_EdadCuy;


    @FXML
    private TextArea adCuyo_Form_Especificaciones;

    @FXML
    private TextField adCuyo_Form_NombreCliente;
    @FXML
    private TextField adCuyo_Form_CelCliente;

    @FXML
    private TextField adCuyo_Form_CorreoCli;

    @FXML
    private TextField adCuyo_Form_DirClie;

    @FXML
    private TextField adCuyo_Form_NombreCuy;

    @FXML
    private TableView<CuyoConDueño> adCuyo_Form_TableViewCuyo;

    @FXML
    private TableColumn<DueñoCuyo, String> adCuyo_Form_TableViewCuyo_CelCuy;

    @FXML
    private TableColumn<DueñoCuyo, String> adCuyo_Form_TableViewCuyo_ClienteCuy;

    @FXML
    private TableColumn<Cuyo, String> adCuyo_Form_TableViewCuyo_EspecificacionesCuy;

    @FXML
    private TableColumn<Cuyo, String> adCuyo_Form_TableViewCuyo_NombreCuy;


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
                addAlojamiento_Form.setVisible(false);
            } else if (clickedButton == employee_btn) {
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(true);
                adCuyo_Form.setVisible(false);
                addVeterinario_Form.setVisible(false);
                addAlojamiento_Form.setVisible(false);
            } else if (clickedButton == addCuyo_btn) {
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(false);
                adCuyo_Form.setVisible(true);
                addVeterinario_Form.setVisible(false);
                addAlojamiento_Form.setVisible(false);
            }else if(clickedButton==addVeterinarioBtn){
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(false);
                adCuyo_Form.setVisible(false);
                addVeterinario_Form.setVisible(true);
                addAlojamiento_Form.setVisible(false);
            }else if(clickedButton==addHotelBtn){
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(false);
                adCuyo_Form.setVisible(false);
                addVeterinario_Form.setVisible(false);
                addAlojamiento_Form.setVisible(true);
            }
        }
    }

    /*-------------------------------------------METODOS DE EMPLEADOOOOOOOOOOOOOOOO-------------------------------------------*/
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
        addEmployee_Nombre_Empleado.setText(String.valueOf(empleadoD.getNombre()));
        addEmployee_Telefono.setText(String.valueOf(empleadoD.getTelefono()));
        addEmployee_sueldo.setText(String.valueOf(empleadoD.getSueldo()));
        addEmployee_Direccion_Empleado.setText(String.valueOf(empleadoD.getDireccion()));
        addEmployee_FechaNac.setValue(empleadoD.getFecha_nacimiento());
        if(empleadoD.getSexo().equals("M")) addEmployee_Genero.setValue("Masculino");
        if(empleadoD.getSexo().equals("F")) addEmployee_Genero.setValue("Femenino");
        id_empleado=empleadoD.getId();
        nombreActual = empleadoD.getNombre();
        fechaNacActual = empleadoD.getFecha_nacimiento();
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
            prepare.setString(7, addEmployee_Genero.getSelectionModel().getSelectedItem());
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
    /*-----------------------------------------------------------------------------------------------------*/

    /*--------------------------------------------Metodos para el Cuyo------------------------------------------------*/

    public ObservableList<CuyoConDueño> addCuyoConDueñoListData() {
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

    public void addCuyoConDueñoShowListData() {
        ObservableList<CuyoConDueño> list = addCuyoConDueñoListData();

        adCuyo_Form_TableViewCuyo_CelCuy.setCellValueFactory(new PropertyValueFactory<>("celularCliente"));
        adCuyo_Form_TableViewCuyo_ClienteCuy.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        adCuyo_Form_TableViewCuyo_EspecificacionesCuy.setCellValueFactory(new PropertyValueFactory<>("especificacionesCuyo"));
        adCuyo_Form_TableViewCuyo_NombreCuy.setCellValueFactory(new PropertyValueFactory<>("nombreCuyo"));

        adCuyo_Form_TableViewCuyo.setItems(list);
    }

    public void addCuyoConDueño() throws SQLException {
        connect = ConnectionBD.getConexion();
        int idCuyoGenerado=0;
        String callProcedureSQL = "{CALL InsertarCuyo(?,?,?)}";
        try {
            connect.setAutoCommit(false);  // Deshabilitar la confirmación automático
            PreparedStatement prepare = connect.prepareStatement(callProcedureSQL, Statement.RETURN_GENERATED_KEYS);
            prepare.setString(1, adCuyo_Form_NombreCuy.getText());
            prepare.setInt(2, Integer.parseInt(adCuyo_Form_EdadCuy.getText()));
            prepare.setString(3, adCuyo_Form_Especificaciones.getText());
            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                connect.commit();  // Confirmar la transacción si hay filas afectadas
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información Operación");
                alert.setHeaderText(null);
                alert.setContentText("Agregado Correctamente");
                alert.showAndWait();
                try (ResultSet rs = prepare.getGeneratedKeys()) {
                    if (rs.next()) {
                        idCuyoGenerado=(rs.getInt("IdGenerado"));
                    }
                }
                System.out.println(idCuyoGenerado+"holaaaa");
                    /*Llamamos el procedure para insertar al dueño*/
                    String callProcedureSQLDueño = "{CALL InsertarDueñoCuyo(?,?,?,?,?)}";
                    try {
                        connect.setAutoCommit(false);  // Deshabilitar la confirmación automático
                        PreparedStatement prepare2 = connect.prepareStatement(callProcedureSQLDueño);
                        prepare2.setInt(1, idCuyoGenerado);
                        prepare2.setString(2, adCuyo_Form_NombreCliente.getText());
                        prepare2.setString(3, adCuyo_Form_CelCliente.getText());
                        prepare2.setString(4, adCuyo_Form_CorreoCli.getText());
                        prepare2.setString(5, adCuyo_Form_DirClie.getText());
                        int rowsAffected2 = prepare2.executeUpdate();
                        if (rowsAffected2 > 0) {
                            connect.commit();  // Confirmar la transacción si hay filas afectadas
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Información Operación");
                            alert2.setHeaderText(null);
                            alert2.setContentText("Cliente Agregado Correctamente");
                            alert2.showAndWait();
                        }
                    } catch (Exception e) {e.printStackTrace();}

                addCuyoConDueñoShowListData();
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

    /*-------------------------------------Metodos para las consultas------------------------------------------------*/

    /*Consultas y Gráficas*/
    public void contarEmpleados(){
        String callProcedureSQL = "{CALL ContarEmpleados()}";
        try (Connection connect = ConnectionBD.getConexion();
             CallableStatement callableStatement = connect.prepareCall(callProcedureSQL);
             ResultSet rs = callableStatement.executeQuery()) {

            if (rs.next()) {
                int totalEmpleados = rs.getInt("TotalEmpleados");
                id_Dash_TotalEmploymeent.setText(String.valueOf(totalEmpleados));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void contarCuyos(){
        String callProcedureSQL = "{CALL ContarCuyos()}";
        try (Connection connect = ConnectionBD.getConexion();
             CallableStatement callableStatement = connect.prepareCall(callProcedureSQL);
             ResultSet rs = callableStatement.executeQuery()) {

            if (rs.next()) {
                int totalCuyos = rs.getInt("TotalCuyos");
                id_Dash_CuyosR.setText(String.valueOf(totalCuyos));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEmpleadoShowListData();
        addCuyoConDueñoShowListData();
        contarEmpleados();
        contarCuyos();
    }
}
