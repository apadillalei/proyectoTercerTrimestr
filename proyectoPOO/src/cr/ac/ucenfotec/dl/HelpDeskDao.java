package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.Departamento;
import cr.ac.ucenfotec.bl.entities.Diccionario;
import cr.ac.ucenfotec.bl.entities.Palabra;
import cr.ac.ucenfotec.bl.entities.Ticket;
import cr.ac.ucenfotec.bl.entities.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Proporciona operaciones de acceso a datos para el sistema HelpDesk U.
 * Gestiona la persistencia de usuarios, departamentos, tickets,
 * diccionarios y palabras.
 */
public class HelpDeskDao {

    // ------------ USUARIOS ------------

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param u usuario a registrar
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
     *
     * @return lista de usuarios
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
     * Busca un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario encontrado o {@code null} si no existe
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
     * Busca un usuario por su correo y contraseña (ya hasheada).
     *
     * @param correo           correo electrónico
     * @param passwordHasheado contraseña en formato hash
     * @return usuario encontrado o {@code null} si las credenciales no coinciden
     */
    public Usuario buscarUsuarioPorCredenciales(String correo, String passwordHasheado) {

        String sql = "SELECT idUsuario, nombre, correo, password, telefono, rol " +
                "FROM Usuarios WHERE correo = ? AND password = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo.trim());
            ps.setString(2, passwordHasheado.trim());

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
     * Busca un usuario por su correo electrónico.
     *
     * @param correo correo a buscar
     * @return usuario encontrado o {@code null} si no existe
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
     *
     * @param u usuario con la información actualizada
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
     * Elimina un usuario por su identificador.
     *
     * @param idUsuario identificador del usuario
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

    // ------------ DEPARTAMENTOS ------------

    /**
     * Inserta un nuevo departamento.
     *
     * @param d departamento a registrar
     */
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

    /**
     * Obtiene todos los departamentos registrados.
     *
     * @return lista de departamentos
     */
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

    /**
     * Busca un departamento por su identificador.
     *
     * @param id identificador del departamento
     * @return departamento encontrado o {@code null} si no existe
     */
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

    /**
     * Actualiza los datos de un departamento.
     *
     * @param d departamento con la información actualizada
     */
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

    /**
     * Elimina un departamento por su identificador.
     *
     * @param idDepartamento identificador del departamento
     */
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

    // ------------ TICKETS ------------

    /**
     * Inserta un nuevo ticket en la base de datos.
     *
     * @param t ticket a registrar
     */
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

    /**
     * Obtiene todos los tickets, incluyendo la información del usuario y el departamento asociados.
     *
     * @return lista de tickets
     */
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

    /**
     * Actualiza el estado de un ticket.
     *
     * @param idTicket   identificador del ticket
     * @param nuevoEstado nuevo estado a asignar
     */
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

    /**
     * Elimina un ticket por su identificador.
     *
     * @param idTicket identificador del ticket
     */
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

    // ------------ DICCIONARIOS ------------

    /**
     * Inserta un nuevo diccionario.
     *
     * @param d diccionario a registrar
     */
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

    /**
     * Obtiene todos los diccionarios registrados.
     *
     * @return lista de diccionarios
     */
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

    /**
     * Busca un diccionario por su identificador.
     *
     * @param idDiccionario identificador del diccionario
     * @return diccionario encontrado o {@code null} si no existe
     */
    public Diccionario buscarDiccionarioPorId(int idDiccionario) {
        String sql = "SELECT idDiccionario, tipo FROM Diccionarios WHERE idDiccionario = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDiccionario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Diccionario d = new Diccionario(rs.getString("tipo"));
                    d.setId(rs.getInt("idDiccionario"));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Actualiza el tipo de un diccionario.
     *
     * @param d diccionario con la información actualizada
     */
    public void actualizarDiccionario(Diccionario d) {
        String sql = "UPDATE Diccionarios SET tipo = ? WHERE idDiccionario = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getTipo());
            ps.setInt(2, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un diccionario y sus palabras asociadas.
     *
     * @param idDiccionario identificador del diccionario
     */
    public void eliminarDiccionario(int idDiccionario) {
        String sqlPalabras = "DELETE FROM Palabras WHERE idDiccionario = ?";
        String sqlDic      = "DELETE FROM Diccionarios WHERE idDiccionario = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion()) {

            try (PreparedStatement psP = conn.prepareStatement(sqlPalabras)) {
                psP.setInt(1, idDiccionario);
                psP.executeUpdate();
            }

            try (PreparedStatement psD = conn.prepareStatement(sqlDic)) {
                psD.setInt(1, idDiccionario);
                psD.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------ PALABRAS ------------

    /**
     * Inserta una palabra asociada a un diccionario.
     *
     * @param p             palabra a registrar
     * @param idDiccionario identificador del diccionario
     */
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

    /**
     * Lista las palabras asociadas a un diccionario.
     *
     * @param idDiccionario identificador del diccionario
     * @return lista de palabras
     */
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

    /**
     * Busca una palabra específica dentro de un diccionario.
     *
     * @param idDiccionario identificador del diccionario
     * @param texto         texto de la palabra
     * @return palabra encontrada o {@code null} si no existe
     */
    public Palabra buscarPalabraEnDiccionario(int idDiccionario, String texto) {
        String sql = "SELECT texto, categoria FROM Palabras " +
                "WHERE idDiccionario = ? AND texto = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDiccionario);
            ps.setString(2, texto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Palabra(
                            rs.getString("texto"),
                            rs.getString("categoria")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Actualiza una palabra dentro de un diccionario.
     *
     * @param idDiccionario identificador del diccionario
     * @param textoOriginal texto actual de la palabra
     * @param nuevoTexto    nuevo texto
     * @param nuevaCategoria nueva categoría
     * @return {@code true} si se actualizó alguna fila; {@code false} en caso contrario
     */
    public boolean actualizarPalabraEnDiccionario(int idDiccionario,
                                                  String textoOriginal,
                                                  String nuevoTexto,
                                                  String nuevaCategoria) {

        String sql = "UPDATE Palabras " +
                "SET texto = ?, categoria = ? " +
                "WHERE idDiccionario = ? AND texto = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoTexto);
            ps.setString(2, nuevaCategoria);
            ps.setInt(3, idDiccionario);
            ps.setString(4, textoOriginal);

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una palabra de un diccionario.
     *
     * @param idDiccionario identificador del diccionario
     * @param texto         texto de la palabra
     * @return {@code true} si la palabra fue eliminada; {@code false} en caso contrario
     */
    public boolean eliminarPalabraDeDiccionario(int idDiccionario, String texto) {
        String sql = "DELETE FROM Palabras WHERE idDiccionario = ? AND texto = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDiccionario);
            ps.setString(2, texto);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
