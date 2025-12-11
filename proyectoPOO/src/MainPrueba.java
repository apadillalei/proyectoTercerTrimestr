

import cr.ac.ucenfotec.dl.ConexionSQLServer;

import java.sql.Connection;

public class MainPrueba {

    public static void main(String[] args) {

        try (Connection conn = ConexionSQLServer.obtenerConexion()) {
            System.out.println("Conexión OK usando config.properties");
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
