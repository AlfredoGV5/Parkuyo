package Controlador;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
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
    private TableColumn<?, ?> addEmployee_col_Fecha_Nacimiento;

    @FXML
    private TableColumn<?, ?> addEmployee_col_Id_Empleado;

    @FXML
    private TableColumn<?, ?> addEmployee_col_Nombre;

    @FXML
    private TableColumn<?, ?> addEmployee_col_Sexo;

    @FXML
    private TableColumn<?, ?> addEmployee_col_Sueldo;

    @FXML
    private TableColumn<?, ?> addEmployee_col_Telefono;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private TextField addEmployee_sueldo;

    @FXML
    private TableView<?> addEmployee_tableView;

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

    //Metodo para cerrar
    public void close(){
        System.exit(0);
    }

    public void switchForm(ActionEvent event){
        if (event.getSource() instanceof Button) {
            Button clickedButton = (Button) event.getSource();
            if (clickedButton == home_btn) {
                id_Dash.setVisible(true);
                addEmployee_form.setVisible(false);
            } else if (clickedButton == employee_btn) {
                id_Dash.setVisible(false);
                addEmployee_form.setVisible(true);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
