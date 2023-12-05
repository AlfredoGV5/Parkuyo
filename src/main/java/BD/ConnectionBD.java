package BD;
import java.sql.*;


public class ConnectionBD {
    public static Connection getConexion(){
        String url="jdbc:sqlserver://localhost:1433;"
                +"database=Parkuyo;"
                +"user=sa;"
                +"password=12345678;"
                +"loginTimeout=30;"
                +"trustServerCertificate=true";
        try{
            Connection con=DriverManager.getConnection(url);
            return con;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
}
