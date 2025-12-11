package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.Departamento;
import cr.ac.ucenfotec.bl.entities.Diccionario;
import cr.ac.ucenfotec.bl.entities.Palabra;
import cr.ac.ucenfotec.bl.entities.Ticket;
import cr.ac.ucenfotec.bl.entities.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO general del sistema HelpDesk U.
 *
 * Esta clase reemplaza el antiguo almacenamiento en memoria (Data)
 * y se encarga de realizar las operaciones CRUD contra la base de datos
 * SQL Server para las entidades de dominio.
 */
public class HelpDeskDao {

    // =========================================================
    // USUARIOS
    // =========================================================

    /**
     * Inserta un nuevo usuario en la base de datos.
     */
    public void insertarUsuario(Usuario u) {
        String sql = "INSERT INTO Usuarios (nombre, correo, password, telefono, rol) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getRol());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene todos los usuarios registrados.
     */
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT idUsuario, nombre, correo, password, telefono, rol " +
                "FROM Usuarios";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("password"),
                        rs.getString("telefono"),
                        rs.getString("rol")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Busca un usuario por su id.
     */
    public Usuario buscarUsuarioPorId(int id) {
        String sql = "SELECT idUsuario, nombre, correo, password, telefono, rol " +
                "FROM Usuarios WHERE idUsuario = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("idUsuario"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("password"),
                            rs.getString("telefono"),
                            rs.getString("rol")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Busca un usuario utilizando sus credenciales de acceso (login).
     */
    public Usuario buscarUsuarioPorCredenciales(String correo, String password) {
        String sql = "SELECT idUsuario, nombre, correo, password, telefono, rol " +
                "FROM Usuarios WHERE correo = ? AND password = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("idUsuario"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("password"),
                            rs.getString("telefono"),
                            rs.getString("rol")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Busca un usuario solo por su correo (validar duplicados).
     */
    public Usuario buscarUsuarioPorCorreo(String correo) {
        String sql = "SELECT idUsuario, nombre, correo, password, telefono, rol " +
                "FROM Usuarios WHERE correo = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("idUsuario"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("password"),
                            rs.getString("telefono"),
                            rs.getString("rol")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Actualiza los datos de un usuario.
     */
    public void actualizarUsuario(Usuario u) {
        String sql = "UPDATE Usuarios " +
                "SET nombre = ?, correo = ?, password = ?, telefono = ?, rol = ? " +
                "WHERE idUsuario = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getRol());
            ps.setInt(6, u.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un usuario por su id.
     */
    public void eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM Usuarios WHERE idUsuario = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    // DEPARTAMENTOS
    // =========================================================

    public void insertarDepartamento(Departamento d) {
        String sql = "INSERT INTO Departamentos (nombre, descripcion, correoContacto) " +
                "VALUES (?, ?, ?)";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNombre());
            ps.setString(2, d.getDescripcion());
            ps.setString(3, d.getCorreoContacto());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Departamento> listarDepartamentos() {
        List<Departamento> lista = new ArrayList<>();

        String sql = "SELECT idDepartamento, nombre, descripcion, correoContacto " +
                "FROM Departamentos";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Departamento d = new Departamento(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("correoContacto")
                );
                d.setId(rs.getInt("idDepartamento"));
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Departamento buscarDepartamentoPorId(int id) {
        String sql = "SELECT idDepartamento, nombre, descripcion, correoContacto " +
                "FROM Departamentos WHERE idDepartamento = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Departamento d = new Departamento(
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getString("correoContacto")
                    );
                    d.setId(rs.getInt("idDepartamento"));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void actualizarDepartamento(Departamento d) {
        String sql = "UPDATE Departamentos " +
                "SET nombre = ?, descripcion = ?, correoContacto = ? " +
                "WHERE idDepartamento = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNombre());
            ps.setString(2, d.getDescripcion());
            ps.setString(3, d.getCorreoContacto());
            ps.setInt(4, d.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDepartamento(int idDepartamento) {
        String sql = "DELETE FROM Departamentos WHERE idDepartamento = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDepartamento);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    // TICKETS
    // =========================================================

    public void insertarTicket(Ticket t) {
        String sql = "INSERT INTO Tickets (asunto, descripcion, estado, idUsuario, idDepartamento) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getAsunto());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getEstado());
            ps.setInt(4, t.getUsuario().getId());
            ps.setInt(5, t.getDepartamento().getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ticket> listarTickets() {
        List<Ticket> lista = new ArrayList<>();

        String sql =
                "SELECT " +
                        "  t.idTicket, t.asunto, t.descripcion, t.estado, " +
                        "  u.idUsuario, u.nombre AS nombreUsuario, u.correo, u.password, u.telefono, u.rol, " +
                        "  d.idDepartamento, d.nombre AS nombreDepto, d.descripcion, d.correoContacto " +
                        "FROM Tickets t " +
                        "JOIN Usuarios u ON t.idUsuario = u.idUsuario " +
                        "JOIN Departamentos d ON t.idDepartamento = d.idDepartamento";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombreUsuario"),
                        rs.getString("correo"),
                        rs.getString("password"),
                        rs.getString("telefono"),
                        rs.getString("rol")
                );

                Departamento d = new Departamento(
                        rs.getString("nombreDepto"),
                        rs.getString("descripcion"),
                        rs.getString("correoContacto")
                );
                d.setId(rs.getInt("idDepartamento"));

                Ticket t = new Ticket(
                        rs.getString("asunto"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        u,
                        d
                );

                lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void actualizarEstadoTicket(int idTicket, String nuevoEstado) {
        String sql = "UPDATE Tickets SET estado = ? WHERE idTicket = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idTicket);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarTicket(int idTicket) {
        String sql = "DELETE FROM Tickets WHERE idTicket = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTicket);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    // DICCIONARIOS
    // =========================================================

    public void insertarDiccionario(Diccionario d) {
        String sql = "INSERT INTO Diccionarios (tipo) VALUES (?)";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getTipo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Diccionario> listarDiccionarios() {
        List<Diccionario> lista = new ArrayList<>();

        String sql = "SELECT idDiccionario, tipo FROM Diccionarios";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Diccionario d = new Diccionario(rs.getString("tipo"));
                d.setId(rs.getInt("idDiccionario"));
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // =========================================================
    // PALABRAS
    // =========================================================

    public void insertarPalabra(Palabra p, int idDiccionario) {
        String sql = "INSERT INTO Palabras (texto, categoria, idDiccionario) " +
                "VALUES (?, ?, ?)";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getTexto());
            ps.setString(2, p.getCategoria());
            ps.setInt(3, idDiccionario);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Palabra> listarPalabrasPorDiccionario(int idDiccionario) {
        List<Palabra> lista = new ArrayList<>();

        String sql = "SELECT texto, categoria FROM Palabras WHERE idDiccionario = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDiccionario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Palabra p = new Palabra(
                            rs.getString("texto"),
                            rs.getString("categoria")
                    );
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
