package cr.ac.ucenfotec.dl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para obtener conexiones a SQL Server.
 *
 * Ajusta la URL, el usuario y la contraseña a los datos de tu servidor.
 */
public class ConexionSQLServer {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=pooproyecto;" +
                    "encrypt=true;" +
                    "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASS = "12345";

    /**
     * Devuelve una conexión abierta a la base de datos SQL Server.
     *
     * @return conexión válida o lanza SQLException si falla.
     */
    public static Connection obtenerConexion() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASS);
    }
}
