package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.*;
import cr.ac.ucenfotec.dl.HelpDeskDao;

import java.util.List;

/**
 * Clase que concentra la lógica de negocio del sistema HelpDesk U.
 *
 * El {@code Gestor} coordina la creación y administración de las
 * entidades del dominio, delegando el acceso a datos al {@link HelpDeskDao}.
 */
public class Gestor {

    private final HelpDeskDao dao;

    /**
     * Constructor por defecto.
     *
     * Inicializa la instancia del DAO que será utilizada para todas las
     * operaciones de persistencia.
     */
    public Gestor() {
        this.dao = new HelpDeskDao();
    }

    // =========================================================
    // USUARIOS
    // =========================================================

    /**
     * Registra un nuevo usuario a partir de los datos básicos.
     * Valida que el correo sea único.
     *
     * @return true si se registró, false si el correo ya existe.
     */
    public boolean registrarUsuario(String nombre,
                                    String correo,
                                    String password,
                                    String telefono,
                                    String rol) {

        // Validar correo único
        Usuario existente = dao.buscarUsuarioPorCorreo(correo);
        if (existente != null) {
            return false; // ya existe ese correo
        }

        Usuario u = new Usuario(nombre, correo, password, telefono, rol);
        dao.insertarUsuario(u);
        return true;
    }

    /**
     * Obtiene la lista completa de usuarios registrados.
     */
    public List<Usuario> listarUsuarios() {
        return dao.listarUsuarios();
    }

    /**
     * Busca un usuario por su identificador.
     */
    public Usuario buscarUsuarioPorId(int id) {
        return dao.buscarUsuarioPorId(id);
    }

    /**
     * Intenta autenticar un usuario con correo y contraseña.
     */
    public Usuario login(String correo, String password) {
        return dao.buscarUsuarioPorCredenciales(correo, password);
    }

    // =========================================================
    // DEPARTAMENTOS
    // =========================================================

    public void registrarDepartamento(String nombre,
                                      String descripcion,
                                      String correoContacto) {

        Departamento d = new Departamento(nombre, descripcion, correoContacto);
        dao.insertarDepartamento(d);
    }

    public List<Departamento> listarDepartamentos() {
        return dao.listarDepartamentos();
    }

    public Departamento buscarDepartamentoPorId(int id) {
        return dao.buscarDepartamentoPorId(id);
    }

    // =========================================================
    // TICKETS
    // =========================================================

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

    public List<Ticket> listarTickets() {
        return dao.listarTickets();
    }

    // =========================================================
    // DICCIONARIOS Y PALABRAS
    // =========================================================

    public void registrarDiccionario(String tipo) {
        Diccionario d = new Diccionario(tipo);
        dao.insertarDiccionario(d);
    }

    public List<Diccionario> listarDiccionarios() {
        return dao.listarDiccionarios();
    }

    public void agregarPalabraADiccionario(int idDiccionario,
                                           String texto,
                                           String categoria) {

        Palabra p = new Palabra(texto, categoria);
        dao.insertarPalabra(p, idDiccionario);
    }

    public List<Palabra> listarPalabrasDeDiccionario(int idDiccionario) {
        return dao.listarPalabrasPorDiccionario(idDiccionario);
    }

    // =========================================================
    // ANÁLISIS BAG OF WORDS (BoW)
    // =========================================================

    /**
     * Ejecuta el análisis Bag of Words sobre la descripción de un ticket.
     *
     * @return arreglo [0] = estadoAnimo, [1] = categoriaTecnica.
     */
    public String[] analizarDescripcionTicket(String descripcion) {

        // 1) Cargar todos los diccionarios
        List<Diccionario> diccionarios = dao.listarDiccionarios();

        Diccionario dicEmocional = null;
        Diccionario dicTecnico   = null;

        // 2) Identificar cuál es cuál según el campo "tipo"
        for (Diccionario d : diccionarios) {
            if ("emocional".equalsIgnoreCase(d.getTipo())) {
                dicEmocional = d;
            } else if ("tecnico".equalsIgnoreCase(d.getTipo())) {
                dicTecnico = d;
            }
        }

        // 3) Cargar las palabras desde la BD para cada diccionario
        if (dicEmocional != null) {
            List<Palabra> emoPalabras =
                    dao.listarPalabrasPorDiccionario(dicEmocional.getId());
            dicEmocional.setPalabras(emoPalabras);
        }

        if (dicTecnico != null) {
            List<Palabra> tecPalabras =
                    dao.listarPalabrasPorDiccionario(dicTecnico.getId());
            dicTecnico.setPalabras(tecPalabras);
        }

        // 4) Crear el analizador BoW con los diccionarios cargados
        AnalisisBow analizador = new AnalisisBow(dicTecnico, dicEmocional);

        String estadoAnimo   = analizador.detectarEstadoAnimo(descripcion);
        String categoriaTec  = analizador.sugerirCategoriaTecnica(descripcion);

        return new String[]{estadoAnimo, categoriaTec};
    }
}
