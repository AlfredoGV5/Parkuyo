package Controlador;

import BD.ConnectionBD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    public TextField user;
    public PasswordField password;
    @FXML
    private Label welcomeText;

    @FXML
    protected void buttonLogin() {
        String usuario=user.getText();
        String passwor= password.getText();
        String consulta= "SELECT contraseña from usuario where usuario=? ";
        try(PreparedStatement preparedStatement= ConnectionBD.getConexion().prepareStatement(consulta)){
            preparedStatement.setString(1,usuario);
            ResultSet resultado = preparedStatement.executeQuery();
            //Verificamos que si exista una consulta que hacer (Que si exista el usuario)
            if (resultado.next()) {
                // Obtener la contraseña almacenada en la base de datos
                String contraseñaBD = resultado.getString("contraseña");
                // Comparar la contraseña ingresada con la almacenada
                if (passwor.equals(contraseñaBD)) {
                    //Obtenemos el nodo al que pertenece User, para despues cerrar la ventana
                    user.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/com/poo/parkuyo/Inicio.fxml"));
                    Stage stage= new Stage();
                    Scene scene=new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    welcomeText.setText("Contraseña incorrecta");
                }
            } else {
                // No se encontró el usuario
                welcomeText.setText("Usuario no encontrado");
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Chingo a su madre "+e);
        }
    }
}