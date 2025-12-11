package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.*;
import cr.ac.ucenfotec.dl.HelpDeskDao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * Gestiona la lógica de negocio del sistema HelpDesk U.
 * Coordina las operaciones relacionadas con usuarios, departamentos,
 * tickets, diccionarios, palabras y análisis de texto.
 */
public class Gestor {

    /** Acceso a la capa de datos del sistema. */
    private final HelpDeskDao dao;

    /**
     * Crea una nueva instancia de {@code Gestor} inicializando
     * el objeto de acceso a datos.
     */
    public Gestor() {
        this.dao = new HelpDeskDao();
    }

    /**
     * Calcula el hash SHA-256 para una cadena de texto.
     *
     * @param plain texto en claro
     * @return hash en formato hexadecimal o {@code null} si el texto es nulo
     */
    private String hashSHA256(String plain) {
        if (plain == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(plain.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return plain;
        }
    }

    // ================= USUARIOS =================

    /**
     * Registra un nuevo usuario si no existe otro con el mismo correo.
     *
     * @param nombre   nombre completo del usuario
     * @param correo   correo electrónico
     * @param password contraseña en texto plano
     * @param telefono número telefónico
     * @param rol      rol dentro del sistema
     * @return {@code true} si se registra el usuario; {@code false} si el correo ya existe
     */
    public boolean registrarUsuario(String nombre,
                                    String correo,
                                    String password,
                                    String telefono,
                                    String rol) {

        Usuario existente = dao.buscarUsuarioPorCorreo(correo);
        if (existente != null) {
            return false;
        }

        String passwordHasheado = hashSHA256(password);
        Usuario u = new Usuario(nombre, correo, passwordHasheado, telefono, rol);
        dao.insertarUsuario(u);
        return true;
    }

    /**
     * Obtiene la lista de usuarios registrados.
     *
     * @return lista de usuarios
     */
    public List<Usuario> listarUsuarios() {
        return dao.listarUsuarios();
    }

    /**
     * Busca un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario encontrado o {@code null} si no existe
     */
    public Usuario buscarUsuarioPorId(int id) {
        return dao.buscarUsuarioPorId(id);
    }

    /**
     * Valida las credenciales de acceso de un usuario.
     *
     * @param correo   correo electrónico
     * @param password contraseña en texto plano
     * @return usuario autenticado o {@code null} si las credenciales son inválidas
     */
    public Usuario login(String correo, String password) {
        String passwordHasheado = hashSHA256(password);
        return dao.buscarUsuarioPorCredenciales(correo, passwordHasheado);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id       identificador del usuario
     * @param nombre   nuevo nombre
     * @param correo   nuevo correo
     * @param password nueva contraseña en texto plano
     * @param telefono nuevo teléfono
     * @param rol      nuevo rol
     * @return {@code true} si se actualiza el usuario; {@code false} si no existe
     */
    public boolean actualizarUsuario(int id,
                                     String nombre,
                                     String correo,
                                     String password,
                                     String telefono,
                                     String rol) {

        Usuario u = dao.buscarUsuarioPorId(id);
        if (u == null) return false;

        String passwordHasheado = hashSHA256(password);

        u.setNombre(nombre);
        u.setCorreo(correo);
        u.setPassword(passwordHasheado);
        u.setTelefono(telefono);
        u.setRol(rol);

        dao.actualizarUsuario(u);
        return true;
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return {@code true} siempre que la operación se ejecuta
     */
    public boolean eliminarUsuario(int id) {
        dao.eliminarUsuario(id);
        return true;
    }

    // ================= DEPARTAMENTOS =================

    /**
     * Registra un nuevo departamento.
     *
     * @param nombre          nombre del departamento
     * @param descripcion     descripción del departamento
     * @param correoContacto  correo de contacto
     */
    public void registrarDepartamento(String nombre,
                                      String descripcion,
                                      String correoContacto) {

        Departamento d = new Departamento(nombre, descripcion, correoContacto);
        dao.insertarDepartamento(d);
    }

    /**
     * Obtiene la lista de departamentos registrados.
     *
     * @return lista de departamentos
     */
    public List<Departamento> listarDepartamentos() {
        return dao.listarDepartamentos();
    }

    /**
     * Busca un departamento por su identificador.
     *
     * @param id identificador del departamento
     * @return departamento encontrado o {@code null} si no existe
     */
    public Departamento buscarDepartamentoPorId(int id) {
        return dao.buscarDepartamentoPorId(id);
    }

    /**
     * Actualiza la información de un departamento.
     *
     * @param id              identificador del departamento
     * @param nombre          nuevo nombre
     * @param descripcion     nueva descripción
     * @param correoContacto  nuevo correo de contacto
     * @return {@code true} si se actualiza; {@code false} si el departamento no existe
     */
    public boolean actualizarDepartamento(int id,
                                          String nombre,
                                          String descripcion,
                                          String correoContacto) {

        Departamento d = dao.buscarDepartamentoPorId(id);
        if (d == null) return false;

        d.setNombre(nombre);
        d.setDescripcion(descripcion);
        d.setCorreoContacto(correoContacto);

        dao.actualizarDepartamento(d);
        return true;
    }

    /**
     * Elimina un departamento por su identificador.
     *
     * @param id identificador del departamento
     * @return {@code true} siempre que la operación se ejecuta
     */
    public boolean eliminarDepartamento(int id) {
        dao.eliminarDepartamento(id);
        return true;
    }

    // ================= TICKETS =================

    /**
     * Registra un nuevo ticket asociado a un usuario y a un departamento.
     * Si el usuario o el departamento no existen, no realiza ninguna acción.
     *
     * @param asunto         asunto del ticket
     * @param descripcion    descripción del ticket
     * @param estado         estado inicial del ticket
     * @param idUsuario      identificador del usuario
     * @param idDepartamento identificador del departamento
     */
    public void registrarTicket(String asunto,
                                String descripcion,
                                String estado,
                                int idUsuario,
                                int idDepartamento) {

        Usuario u = dao.buscarUsuarioPorId(idUsuario);
        Departamento d = dao.buscarDepartamentoPorId(idDepartamento);

        if (u == null || d == null) {
            return;
        }

        Ticket t = new Ticket(asunto, descripcion, estado, u, d);
        dao.insertarTicket(t);
    }

    /**
     * Obtiene la lista de tickets registrados.
     *
     * @return lista de tickets
     */
    public List<Ticket> listarTickets() {
        return dao.listarTickets();
    }

    // ================= DICCIONARIOS Y PALABRAS =================

    /**
     * Registra un nuevo diccionario.
     *
     * @param tipo tipo del diccionario
     */
    public void registrarDiccionario(String tipo) {
        Diccionario d = new Diccionario(tipo);
        dao.insertarDiccionario(d);
    }

    /**
     * Obtiene la lista de diccionarios registrados.
     *
     * @return lista de diccionarios
     */
    public List<Diccionario> listarDiccionarios() {
        return dao.listarDiccionarios();
    }

    /**
     * Actualiza el tipo de un diccionario.
     *
     * @param idDiccionario identificador del diccionario
     * @param nuevoTipo     nuevo tipo
     * @return {@code true} si se actualiza; {@code false} si el diccionario no existe
     */
    public boolean actualizarDiccionario(int idDiccionario, String nuevoTipo) {
        Diccionario d = dao.buscarDiccionarioPorId(idDiccionario);
        if (d == null) return false;
        d.setTipo(nuevoTipo);
        dao.actualizarDiccionario(d);
        return true;
    }

    /**
     * Elimina un diccionario por su identificador.
     *
     * @param idDiccionario identificador del diccionario
     * @return {@code true} si el diccionario existe y se elimina; {@code false} en caso contrario
     */
    public boolean eliminarDiccionario(int idDiccionario) {
        Diccionario d = dao.buscarDiccionarioPorId(idDiccionario);
        if (d == null) return false;
        dao.eliminarDiccionario(idDiccionario);
        return true;
    }

    /**
     * Agrega una palabra a un diccionario, siempre que no exista ya una
     * palabra igual en el mismo diccionario.
     *
     * @param idDiccionario identificador del diccionario
     * @param texto         texto de la palabra
     * @param categoria     categoría asociada
     * @return {@code true} si se agrega la palabra; {@code false} si ya existe
     */
    public boolean agregarPalabraADiccionario(int idDiccionario,
                                              String texto,
                                              String categoria) {

        String normalizado = texto.toLowerCase().trim();
        Palabra existente = dao.buscarPalabraEnDiccionario(idDiccionario, normalizado);
        if (existente != null) {
            return false;
        }

        Palabra p = new Palabra(normalizado, categoria);
        dao.insertarPalabra(p, idDiccionario);
        return true;
    }

    /**
     * Lista las palabras asociadas a un diccionario.
     *
     * @param idDiccionario identificador del diccionario
     * @return lista de palabras
     */
    public List<Palabra> listarPalabrasDeDiccionario(int idDiccionario) {
        return dao.listarPalabrasPorDiccionario(idDiccionario);
    }

    /**
     * Actualiza una palabra dentro de un diccionario.
     *
     * @param idDiccionario identificador del diccionario
     * @param textoOriginal texto original de la palabra
     * @param nuevoTexto    nuevo texto
     * @param nuevaCategoria nueva categoría
     * @return {@code true} si la actualización fue exitosa; {@code false} en caso contrario
     */
    public boolean actualizarPalabraEnDiccionario(int idDiccionario,
                                                  String textoOriginal,
                                                  String nuevoTexto,
                                                  String nuevaCategoria) {

        String nuevoNormalizado = nuevoTexto.toLowerCase().trim();
        return dao.actualizarPalabraEnDiccionario(
                idDiccionario,
                textoOriginal,
                nuevoNormalizado,
                nuevaCategoria
        );
    }

    /**
     * Elimina una palabra de un diccionario.
     *
     * @param idDiccionario identificador del diccionario
     * @param texto         texto de la palabra a eliminar
     * @return {@code true} si la eliminación fue exitosa; {@code false} en caso contrario
     */
    public boolean eliminarPalabraDeDiccionario(int idDiccionario, String texto) {
        return dao.eliminarPalabraDeDiccionario(idDiccionario, texto);
    }

    // ================= ANÁLISIS BAG OF WORDS =================

    /**
     * Analiza una descripción de ticket para determinar el estado de ánimo
     * y la categoría técnica predominante.
     *
     * @param descripcion descripción del ticket
     * @return arreglo con dos posiciones:
     *         [0] estado de ánimo,
     *         [1] categoría técnica
     */
    public String[] analizarDescripcionTicket(String descripcion) {

        List<Diccionario> diccionarios = dao.listarDiccionarios();

        Diccionario dicEmocional = null;
        Diccionario dicTecnico   = null;

        for (Diccionario d : diccionarios) {
            if ("emocional".equalsIgnoreCase(d.getTipo())) {
                dicEmocional = d;
            } else if ("tecnico".equalsIgnoreCase(d.getTipo())) {
                dicTecnico = d;
            }
        }

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

        AnalisisBow analizador = new AnalisisBow(dicTecnico, dicEmocional);

        String estadoAnimo   = analizador.detectarEstadoAnimo(descripcion);
        String categoriaTec  = analizador.sugerirCategoriaTecnica(descripcion);

        return new String[]{estadoAnimo, categoriaTec};
    }

    /**
     * Analiza una descripción de ticket de forma detallada, incluyendo
     * el vector de frecuencias y las palabras detectadas.
     *
     * @param descripcion descripción del ticket
     * @return arreglo con cuatro posiciones:
     *         [0] estado de ánimo,
     *         [1] categoría técnica,
     *         [2] mapa TF como texto,
     *         [3] lista de palabras detectadas
     */
    public String[] analizarDescripcionTicketDetallado(String descripcion) {

        List<Diccionario> diccionarios = dao.listarDiccionarios();

        Diccionario dicEmocional = null;
        Diccionario dicTecnico   = null;

        for (Diccionario d : diccionarios) {
            if ("emocional".equalsIgnoreCase(d.getTipo())) {
                dicEmocional = d;
            } else if ("tecnico".equalsIgnoreCase(d.getTipo())) {
                dicTecnico = d;
            }
        }

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

        AnalisisBow analizador = new AnalisisBow(dicTecnico, dicEmocional);

        String estadoAnimo   = analizador.detectarEstadoAnimo(descripcion);
        String categoriaTec  = analizador.sugerirCategoriaTecnica(descripcion);

        Map<String, Integer> tfMap = analizador.vectorizarTFMap(descripcion);
        String tfComoTexto = analizador.tfMapToString(tfMap);

        StringBuilder sb = new StringBuilder();
        for (String palabra : tfMap.keySet()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(palabra);
        }
        String palabrasDetectadas = sb.toString();

        return new String[]{estadoAnimo, categoriaTec, tfComoTexto, palabrasDetectadas};
    }
}
