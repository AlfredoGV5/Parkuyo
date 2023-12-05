package Controlador;

import BD.ConnectionBD;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        String bases="";
        try{
            Statement sql= ConnectionBD.getConexion().createStatement();
            String consulta="SELECT contraseña from usuario";
            ResultSet resultado= sql.executeQuery(consulta);
            while(resultado.next()){
                System.out.println("hola");
                bases += resultado.getString(1);
            }
            System.out.println(bases);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}