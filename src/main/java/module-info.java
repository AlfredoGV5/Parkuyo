module com.poo.parkuyo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.poo.parkuyo to javafx.fxml;
    exports com.poo.parkuyo;
    exports Controlador;
    opens Controlador to javafx.fxml;
}