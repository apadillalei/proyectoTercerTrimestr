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
 * <p>Esta clase reemplaza el antiguo almacenamiento en memoria (Data)
 * y se encarga de realizar las operaciones CRUD contra la base de datos
 * SQL Server para las entidades de dominio:
 * {@link Usuario}, {@link Departamento}, {@link Ticket},
 * {@link Diccionario} y {@link Palabra}.</p>
 *
 * <p>La obtención de la conexión se delega a la clase de utilidad
 * {@code ConexionSQLServer}, que debe exponer un método estático
 * {@code obtenerConexion()} que retorne un {@link Connection} válido.</p>
 *
 * <p><b>Nota:</b> Los nombres de tablas y columnas usados en las sentencias
 * SQL son de ejemplo y deben ajustarse al esquema real de la base de datos.</p>
 */
public class HelpDeskDao {

    // =========================================================
    // USUARIOS
    // =========================================================

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param u usuario a registrar.
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
            // En un proyecto real: registrar en log o lanzar excepción propia.
        }
    }

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     *
     * @return lista de usuarios.
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
     * @param id identificador del usuario.
     * @return usuario encontrado o {@code null} si no existe.
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
     * Busca un usuario utilizando sus credenciales de acceso.
     *
     * @param correo   correo electrónico.
     * @param password contraseña.
     * @return usuario autenticado o {@code null} si las credenciales no coinciden.
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

    // =========================================================
    // DEPARTAMENTOS
    // =========================================================

    /**
     * Inserta un nuevo departamento en la base de datos.
     *
     * @param d departamento a registrar.
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
     * Devuelve todos los departamentos almacenados.
     *
     * @return lista de departamentos.
     */
    public List<Departamento> listarDepartamentos() {
        List<Departamento> lista = new ArrayList<>();

        String sql = "SELECT idDepto, nombre, descripcion, correoContacto " +
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
                d.setId(rs.getInt("idDepto"));
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
     * @param id identificador del departamento.
     * @return departamento encontrado o {@code null} si no existe.
     */
    public Departamento buscarDepartamentoPorId(int id) {
        String sql = "SELECT idDepto, nombre, descripcion, correoContacto " +
                "FROM Departamentos WHERE idDepto = ?";

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
                    d.setId(rs.getInt("idDepto"));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // =========================================================
    // TICKETS
    // =========================================================

    /**
     * Inserta un nuevo ticket en la base de datos.
     *
     * <p>La entidad {@link Ticket} recibe el {@link Usuario} y el
     * {@link Departamento} ya cargados desde la capa de negocio.
     * Aquí solo se usan sus IDs para guardar el registro.</p>
     *
     * @param t ticket a registrar.
     */
    public void insertarTicket(Ticket t) {
        String sql = "INSERT INTO Tickets (asunto, descripcion, estado, idUsuario, idDepto) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getAsunto());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getEstado());
            ps.setInt(4, t.getUsuario().getId());
            ps.setInt(5, t.getDepartamento().getId());

            ps.executeUpdate();   // No intentamos setear el id en la entidad.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista todos los tickets registrados, incluyendo los datos
     * básicos del usuario y del departamento asociados.
     *
     * @return lista de tickets.
     */
    public List<Ticket> listarTickets() {
        List<Ticket> lista = new ArrayList<>();

        String sql =
                "SELECT " +
                        "  t.idTicket, t.asunto, t.descripcion, t.estado, " +
                        "  u.idUsuario, u.nombre AS nombreUsuario, u.correo, u.password, u.telefono, u.rol, " +
                        "  d.idDepto, d.nombre AS nombreDepto, d.descripcion, d.correoContacto " +
                        "FROM Tickets t " +
                        "JOIN Usuarios u ON t.idUsuario = u.idUsuario " +
                        "JOIN Departamentos d ON t.idDepto = d.idDepto";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                // Construimos el usuario asociado al ticket
                Usuario u = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombreUsuario"),
                        rs.getString("correo"),
                        rs.getString("password"),
                        rs.getString("telefono"),
                        rs.getString("rol")
                );

                // Construimos el departamento asociado
                Departamento d = new Departamento(
                        rs.getString("nombreDepto"),
                        rs.getString("descripcion"),
                        rs.getString("correoContacto")
                );
                d.setId(rs.getInt("idDepto"));

                // Construimos el ticket usando tu constructor actual
                Ticket t = new Ticket(
                        rs.getString("asunto"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        u,
                        d
                );

                // OJO: el id interno del Ticket se genera con SEQ.
                // Si algún día quieres que coincida con idTicket,
                // habría que agregar setId(int) en la entidad.
                lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // =========================================================
    // DICCIONARIOS
    // =========================================================

    /**
     * Inserta un nuevo diccionario en la base de datos.
     *
     * @param d diccionario a registrar.
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
     * Lista todos los diccionarios existentes.
     *
     * @return lista de diccionarios.
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

    // =========================================================
    // PALABRAS
    // =========================================================

    /**
     * Inserta una palabra asociada a un diccionario.
     *
     * @param p             palabra a registrar.
     * @param idDiccionario identificador del diccionario al que pertenece.
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
     * Lista todas las palabras pertenecientes a un diccionario.
     *
     * @param idDiccionario identificador del diccionario.
     * @return lista de palabras asociadas.
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
}
