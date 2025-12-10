package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.Departamento;
import cr.ac.ucenfotec.bl.entities.Diccionario;
import cr.ac.ucenfotec.bl.entities.Palabra;
import cr.ac.ucenfotec.bl.entities.Ticket;
import cr.ac.ucenfotec.bl.entities.Usuario;
import cr.ac.ucenfotec.dl.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que concentra la lógica de negocio del sistema HelpDesk U.
 *
 * El {@code Gestor} se encarga de coordinar la creación, almacenamiento,
 * búsqueda y administración de entidades como Usuarios, Departamentos,
 * Tickets y Diccionarios. Para almacenar los datos utiliza la clase
 * {@link cr.ac.ucenfotec.dl.Data}.
 *
 * También administra la asignación de identificadores incrementales para
 * Usuarios, Departamentos y Diccionarios, manteniendo el control del estado
 * del sistema mientras se ejecuta en memoria.
 *
 * Esta clase es utilizada por la capa de UI a través del Controller,
 * garantizando así una correcta separación de responsabilidades dentro
 * de la arquitectura del proyecto.
 */
public class Gestor {

    private final Data data;

    // Contadores para asignar IDs en memoria
    private int contadorUsuario = 1;
    private int contadorDepartamento = 1;
    private int contadorDiccionario = 1;

    public Gestor() {
        data = new Data();
    }

    // =========================================================
    // USUARIO
    // =========================================================

    /**
     * Registra un nuevo usuario a partir de los datos básicos.
     *
     * @param nombre   nombre completo del usuario
     * @param correo   correo de contacto / login
     * @param password contraseña para el inicio de sesión
     * @param telefono teléfono de contacto
     * @param rol      rol del usuario en el sistema
     */
    public void registrarUsuario(String nombre,
                                 String correo,
                                 String password,
                                 String telefono,
                                 String rol) {

        Usuario u = new Usuario(nombre, correo, password, telefono, rol);
        u.setId(contadorUsuario++);
        data.getUsuarios().add(u);
    }

    /**
     * Devuelve la lista interna de usuarios (capa de lógica).
     *
     * @return lista de objetos Usuario
     */
    public List<Usuario> listarUsuarios() {
        return data.getUsuarios();
    }

    /**
     * Devuelve la lista de usuarios en formato de texto,
     * lista para ser impresa en la capa de UI.
     *
     * @return lista de strings con la representación textual de cada usuario
     */
    public List<String> listarUsuariosComoTexto() {
        List<String> out = new ArrayList<>();
        for (Usuario u : data.getUsuarios()) {
            out.add(u.toString());
        }
        return out;
    }

    /**
     * Busca un usuario por su identificador interno.
     *
     * @param id identificador del usuario
     * @return el usuario encontrado o {@code null} si no existe
     */
    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : data.getUsuarios()) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    /**
     * Intenta autenticar un usuario con correo y contraseña.
     *
     * @param correo   correo ingresado
     * @param password contraseña ingresada
     * @return el usuario autenticado o {@code null} si no coincide
     */
    public Usuario login(String correo, String password) {
        for (Usuario u : data.getUsuarios()) {
            if (u.getCorreo().equalsIgnoreCase(correo)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Versión simplificada del login para la capa UI.
     * Devuelve solo el nombre del usuario autenticado.
     *
     * @param correo   correo ingresado
     * @param password contraseña ingresada
     * @return nombre del usuario autenticado o {@code null} si no coincide
     */
    public String iniciarSesion(String correo, String password) {
        Usuario u = login(correo, password);
        return (u != null) ? u.getNombre() : null;
    }

    // =========================================================
    // DEPARTAMENTO
    // =========================================================

    /**
     * Registra un nuevo departamento.
     *
     * @param nombre          nombre del departamento
     * @param descripcion     breve descripción de sus funciones
     * @param correoContacto  correo de contacto
     */
    public void registrarDepartamento(String nombre,
                                      String descripcion,
                                      String correoContacto) {

        Departamento d = new Departamento(nombre, descripcion, correoContacto);
        d.setId(contadorDepartamento++);
        data.getDepartamentos().add(d);
    }

    /**
     * Lista de departamentos como objetos de dominio.
     *
     * @return lista de departamentos
     */
    public List<Departamento> listarDepartamentos() {
        return data.getDepartamentos();
    }

    /**
     * Lista de departamentos en formato de texto para la UI.
     *
     * @return lista de strings con la representación textual de cada departamento
     */
    public List<String> listarDepartamentosComoTexto() {
        List<String> out = new ArrayList<>();
        for (Departamento d : data.getDepartamentos()) {
            out.add(d.toString());
        }
        return out;
    }

    /**
     * Busca un departamento por id.
     *
     * @param id identificador del departamento
     * @return el departamento encontrado o {@code null} si no existe
     */
    public Departamento buscarDepartamentoPorId(int id) {
        for (Departamento d : data.getDepartamentos()) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    // =========================================================
    // TICKET
    // =========================================================

    /**
     * Registra un nuevo ticket asociado a un usuario y un departamento.
     *
     * @param asunto         asunto del ticket
     * @param descripcion    descripción del problema
     * @param estado         estado inicial del ticket
     * @param idUsuario      identificador del usuario que reporta
     * @param idDepartamento identificador del departamento asignado
     */
    public void registrarTicket(String asunto,
                                String descripcion,
                                String estado,
                                int idUsuario,
                                int idDepartamento) {

        Usuario u = buscarUsuarioPorId(idUsuario);
        Departamento d = buscarDepartamentoPorId(idDepartamento);

        if (u == null || d == null) {
            // En un proyecto real se podría lanzar una excepción.
            return;
        }

        Ticket t = new Ticket(asunto, descripcion, estado, u, d);
        data.getTickets().add(t);
    }

    /**
     * Lista de tickets como objetos de dominio.
     *
     * @return lista de tickets
     */
    public List<Ticket> listarTickets() {
        return data.getTickets();
    }

    /**
     * Lista de tickets en formato de texto para la UI.
     *
     * @return lista de strings con la representación textual de cada ticket
     */
    public List<String> listarTicketsComoTexto() {
        List<String> out = new ArrayList<>();
        for (Ticket t : data.getTickets()) {
            out.add(t.toString());
        }
        return out;
    }

    /**
     * Busca un ticket por id.
     *
     * @param id identificador del ticket
     * @return el ticket encontrado o {@code null} si no existe
     */
    public Ticket buscarTicketPorId(int id) {
        for (Ticket t : data.getTickets()) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    // =========================================================
    // DICCIONARIO Y PALABRAS
    // =========================================================

    /**
     * Crea y registra un nuevo diccionario (por ejemplo, emocional o técnico).
     *
     * @param tipo tipo de diccionario
     */
    public void registrarDiccionario(String tipo) {
        Diccionario d = new Diccionario(tipo);
        d.setId(contadorDiccionario++);
        data.getDiccionarios().add(d);
    }

    /**
     * Lista de diccionarios como objetos de dominio.
     *
     * @return lista de diccionarios
     */
    public List<Diccionario> listarDiccionarios() {
        return data.getDiccionarios();
    }

    /**
     * Lista de diccionarios en formato de texto para la UI.
     *
     * @return lista de strings con la representación textual de cada diccionario
     */
    public List<String> listarDiccionariosComoTexto() {
        List<String> out = new ArrayList<>();
        for (Diccionario d : data.getDiccionarios()) {
            out.add(d.toString());
        }
        return out;
    }

    /**
     * Busca un diccionario por id.
     *
     * @param id identificador del diccionario
     * @return el diccionario encontrado o {@code null} si no existe
     */
    public Diccionario buscarDiccionarioPorId(int id) {
        for (Diccionario d : data.getDiccionarios()) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    /**
     * Agrega una nueva palabra a un diccionario existente.
     *
     * @param idDiccionario identificador del diccionario destino
     * @param texto         texto de la palabra
     * @param categoria     categoría o etiqueta asociada
     */
    public void agregarPalabraADiccionario(int idDiccionario,
                                           String texto,
                                           String categoria) {

        Diccionario dic = buscarDiccionarioPorId(idDiccionario);
        if (dic == null) return;

        Palabra p = new Palabra(texto, categoria);
        dic.getPalabras().add(p);
    }

    /**
     * Devuelve las palabras de un diccionario específico.
     *
     * @param idDiccionario identificador del diccionario
     * @return lista de palabras o {@code null} si no existe el diccionario
     */
    public List<Palabra> listarPalabrasDeDiccionario(int idDiccionario) {
        Diccionario dic = buscarDiccionarioPorId(idDiccionario);
        return (dic == null) ? null : dic.getPalabras();
    }

    /**
     * Devuelve las palabras de un diccionario en formato de texto
     * para ser mostradas en la capa de UI.
     *
     * @param idDiccionario identificador del diccionario
     * @return lista de strings con la representación textual de cada palabra
     */
    public List<String> listarPalabrasDeDiccionarioComoTexto(int idDiccionario) {
        List<String> out = new ArrayList<>();
        Diccionario dic = buscarDiccionarioPorId(idDiccionario);
        if (dic == null) {
            return out;
        }
        for (Palabra p : dic.getPalabras()) {
            out.add(p.toString());
        }
        return out;
    }
}
