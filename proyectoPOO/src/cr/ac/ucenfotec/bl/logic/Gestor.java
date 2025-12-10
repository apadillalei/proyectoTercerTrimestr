package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.*;
import cr.ac.ucenfotec.dl.HelpDeskDao;

import java.util.List;

/**
 * Clase que concentra la lógica de negocio del sistema HelpDesk U.
 *
 * <p>El {@code Gestor} coordina la creación y administración de las
 * entidades del dominio, delegando el acceso a datos al
 * {@link HelpDeskDao}. De esta forma, la capa de UI nunca interactúa
 * directamente con SQL ni con la base de datos.</p>
 *
 * <p>Las reglas de negocio (por ejemplo, validaciones sencillas) deben
 * ubicarse en esta clase, manteniendo a la UI como una capa estrictamente
 * de presentación.</p>
 */
public class Gestor {

    private final HelpDeskDao dao;

    /**
     * Constructor por defecto.
     *
     * <p>Inicializa la instancia del DAO que será utilizada para todas las
     * operaciones de persistencia.</p>
     */
    public Gestor() {
        this.dao = new HelpDeskDao();
    }

    // =========================================================
    // USUARIOS
    // =========================================================

    /**
     * Registra un nuevo usuario a partir de los datos básicos.
     *
     * @param nombre   nombre completo del usuario.
     * @param correo   correo electrónico (también usado como login).
     * @param password contraseña para el inicio de sesión.
     * @param telefono teléfono de contacto.
     * @param rol      rol que desempeña en el sistema.
     */
    public void registrarUsuario(String nombre,
                                 String correo,
                                 String password,
                                 String telefono,
                                 String rol) {

        Usuario u = new Usuario(nombre, correo, password, telefono, rol);
        dao.insertarUsuario(u);
    }

    /**
     * Obtiene la lista completa de usuarios registrados.
     *
     * @return lista de {@link Usuario}.
     */
    public List<Usuario> listarUsuarios() {
        return dao.listarUsuarios();
    }

    /**
     * Busca un usuario por su identificador.
     *
     * @param id identificador del usuario.
     * @return usuario encontrado o {@code null} si no existe.
     */
    public Usuario buscarUsuarioPorId(int id) {
        return dao.buscarUsuarioPorId(id);
    }

    /**
     * Intenta autenticar un usuario con correo y contraseña.
     *
     * @param correo   correo ingresado.
     * @param password contraseña ingresada.
     * @return usuario autenticado o {@code null} si las credenciales no son válidas.
     */
    public Usuario login(String correo, String password) {
        return dao.buscarUsuarioPorCredenciales(correo, password);
    }

    // =========================================================
    // DEPARTAMENTOS
    // =========================================================

    /**
     * Registra un nuevo departamento.
     *
     * @param nombre         nombre del departamento.
     * @param descripcion    breve descripción de sus funciones.
     * @param correoContacto correo de contacto del departamento.
     */
    public void registrarDepartamento(String nombre,
                                      String descripcion,
                                      String correoContacto) {

        Departamento d = new Departamento(nombre, descripcion, correoContacto);
        dao.insertarDepartamento(d);
    }

    /**
     * Lista todos los departamentos registrados.
     *
     * @return lista de {@link Departamento}.
     */
    public List<Departamento> listarDepartamentos() {
        return dao.listarDepartamentos();
    }

    /**
     * Busca un departamento por su id.
     *
     * @param id identificador del departamento.
     * @return departamento encontrado o {@code null} si no existe.
     */
    public Departamento buscarDepartamentoPorId(int id) {
        return dao.buscarDepartamentoPorId(id);
    }

    // =========================================================
    // TICKETS
    // =========================================================

    /**
     * Registra un nuevo ticket asociado a un usuario y un departamento.
     *
     * @param asunto         asunto o título del ticket.
     * @param descripcion    descripción del problema.
     * @param estado         estado inicial del ticket (por ejemplo, "nuevo").
     * @param idUsuario      id del usuario que reporta.
     * @param idDepartamento id del departamento asignado.
     */
    public void registrarTicket(String asunto,
                                String descripcion,
                                String estado,
                                int idUsuario,
                                int idDepartamento) {

        Usuario u = dao.buscarUsuarioPorId(idUsuario);
        Departamento d = dao.buscarDepartamentoPorId(idDepartamento);

        if (u == null || d == null) {
            // En un sistema completo se podría lanzar una excepción personalizada.
            return;
        }

        Ticket t = new Ticket(asunto, descripcion, estado, u, d);
        dao.insertarTicket(t);
    }

    /**
     * Obtiene la lista de todos los tickets registrados.
     *
     * @return lista de {@link Ticket}.
     */
    public List<Ticket> listarTickets() {
        return dao.listarTickets();
    }

    // =========================================================
    // DICCIONARIOS Y PALABRAS
    // =========================================================

    /**
     * Registra un nuevo diccionario.
     *
     * @param tipo tipo de diccionario (por ejemplo, "emocional" o "tecnico").
     */
    public void registrarDiccionario(String tipo) {
        Diccionario d = new Diccionario(tipo);
        dao.insertarDiccionario(d);
    }

    /**
     * Lista todos los diccionarios existentes.
     *
     * @return lista de {@link Diccionario}.
     */
    public List<Diccionario> listarDiccionarios() {
        return dao.listarDiccionarios();
    }

    /**
     * Agrega una nueva palabra a un diccionario existente.
     *
     * @param idDiccionario id del diccionario al que se agregará la palabra.
     * @param texto         texto de la palabra.
     * @param categoria     categoría o emoción asociada.
     */
    public void agregarPalabraADiccionario(int idDiccionario,
                                           String texto,
                                           String categoria) {

        Palabra p = new Palabra(texto, categoria);
        dao.insertarPalabra(p, idDiccionario);
    }

    /**
     * Lista todas las palabras asociadas a un diccionario.
     *
     * @param idDiccionario identificador del diccionario.
     * @return lista de {@link Palabra}.
     */
    public List<Palabra> listarPalabrasDeDiccionario(int idDiccionario) {
        return dao.listarPalabrasPorDiccionario(idDiccionario);
    }
}
