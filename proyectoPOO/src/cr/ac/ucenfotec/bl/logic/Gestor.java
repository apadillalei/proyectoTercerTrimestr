package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.*;
import cr.ac.ucenfotec.dl.HelpDeskDao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class Gestor {

    private final HelpDeskDao dao;

    public Gestor() {
        this.dao = new HelpDeskDao();
    }

    // =========================================================
    // UTILIDAD: HASH DE CONTRASEÑAS
    // =========================================================

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

    // =========================================================
    // USUARIOS
    // =========================================================

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

    public List<Usuario> listarUsuarios() {
        return dao.listarUsuarios();
    }

    public Usuario buscarUsuarioPorId(int id) {
        return dao.buscarUsuarioPorId(id);
    }

    public Usuario login(String correo, String password) {
        String passwordHasheado = hashSHA256(password);
        return dao.buscarUsuarioPorCredenciales(correo, passwordHasheado);
    }

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

    public boolean eliminarUsuario(int id) {
        dao.eliminarUsuario(id);
        return true;
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

    public boolean eliminarDepartamento(int id) {
        dao.eliminarDepartamento(id);
        return true;
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

    public boolean actualizarDiccionario(int idDiccionario, String nuevoTipo) {
        Diccionario d = dao.buscarDiccionarioPorId(idDiccionario);
        if (d == null) return false;
        d.setTipo(nuevoTipo);
        dao.actualizarDiccionario(d);
        return true;
    }

    public boolean eliminarDiccionario(int idDiccionario) {
        Diccionario d = dao.buscarDiccionarioPorId(idDiccionario);
        if (d == null) return false;
        dao.eliminarDiccionario(idDiccionario);
        return true;
    }

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

    public List<Palabra> listarPalabrasDeDiccionario(int idDiccionario) {
        return dao.listarPalabrasPorDiccionario(idDiccionario);
    }

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

    public boolean eliminarPalabraDeDiccionario(int idDiccionario, String texto) {
        return dao.eliminarPalabraDeDiccionario(idDiccionario, texto);
    }

    // =========================================================
    // ANÁLISIS BAG OF WORDS (BoW)
    // =========================================================

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
