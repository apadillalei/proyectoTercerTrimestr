//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cr.ac.ucenfotec.dl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLServer {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=pooproyecto;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "12345";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=pooproyecto;encrypt=false;trustServerCertificate=true;", "sa", "12345");
    }
}
