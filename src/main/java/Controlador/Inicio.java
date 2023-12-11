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
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
public class Inicio implements Initializable{
    @FXML
    private Button addEmployee_AddBtn;

    @FXML
    private Button addEmployee_ClearBtn;

    @FXML
    private Button addEmployee_DeleteBtn;

    @FXML
    private DatePicker addEmployee_FechaNac;

    @FXML
    private ChoiceBox<?> addEmployee_Genero;

    @FXML
    private TextField addEmployee_Nombre_Empleado;

    @FXML
    private ChoiceBox<?> addEmployee_Posicion;

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


    private Connection connect;

    //Metodo para cerrar
    public void close(){
        System.exit(0);
    }

    /*METODO PARA CAMBIAR ENTRE LAS VENTANAS DEL PANEL*/
    public void switchForm(ActionEvent event){
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
            }else if(clickedButton==addCuyo_btn){
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(false);
                adCuyo_Form.setVisible(true);
            }
        }
    }

    /*Metodo para enumerar a los empleados*/
    public ObservableList<Empleado> addEmpleadoListData(){
        String sql="SELECT * FROM empleados";
        ObservableList<Empleado> listData= FXCollections.observableArrayList();
        connect= ConnectionBD.getConexion();

        try(PreparedStatement preparedStatement=connect.prepareStatement(sql);){
            ResultSet resultado = preparedStatement.executeQuery();
            Empleado empleadoD;
            while(resultado.next()){
                empleadoD=new Empleado(resultado.getString("Nombre"),
                        resultado.getString("telefono"),
                        resultado.getString("direccion"),
                        resultado.getFloat("sueldo"),
                        resultado.getString("sexo"),
                        resultado.getDate("fecha_nacimiento"));
                listData.add(empleadoD);
            }
        }catch(Exception e){e.printStackTrace();}
        return listData;
    }

    private ObservableList<Empleado> addEmpleadoList;
    public void addEmpleadoShowListData(){
        addEmpleadoList = addEmpleadoListData();
        addEmployee_col_Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        addEmployee_col_Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        addEmployee_col_Direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        addEmployee_col_Sueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));
        addEmployee_col_Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        addEmployee_col_Fecha_Nacimiento.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));

        addEmployee_tableView.setItems(addEmpleadoList);
    }

    public void addEmployeeSelect(){

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEmpleadoShowListData();
    }
}
