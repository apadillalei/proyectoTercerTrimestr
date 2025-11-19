package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.Departamento;
import cr.ac.ucenfotec.bl.entities.Diccionario;
import cr.ac.ucenfotec.bl.entities.Palabra;
import cr.ac.ucenfotec.bl.entities.Ticket;
import cr.ac.ucenfotec.bl.entities.Usuario;
import cr.ac.ucenfotec.dl.Data;

import java.util.List;

public class Gestor {

    private Data data;

    // Contadores para asignar IDs en memoria
    private int contadorUsuario = 1;
    private int contadorDepartamento = 1;
    private int contadorDiccionario = 1;

    public Gestor() {
        data = new Data();
    }

    // ================== USUARIO ==================

    public void guardarUsuario(Usuario u) {
        if (u.getId() == 0) {
            u.setId(contadorUsuario++);
        }
        data.getUsuarios().add(u);
    }

    public List<Usuario> listarUsuarios() {
        return data.getUsuarios();
    }

    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : data.getUsuarios()) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public Usuario login(String correo, String password) {
        for (Usuario u : data.getUsuarios()) {
            if (u.getCorreo().equalsIgnoreCase(correo)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    // ================== DEPARTAMENTO ==================

    public void guardarDepartamento(Departamento d) {
        if (d.getId() == 0) {
            d.setId(contadorDepartamento++);
        }
        data.getDepartamentos().add(d);
    }

    public List<Departamento> listarDepartamentos() {
        return data.getDepartamentos();
    }

    public Departamento buscarDepartamentoPorId(int id) {
        for (Departamento d : data.getDepartamentos()) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    // ================== TICKET ==================

    public void guardarTicket(Ticket t) {
        // Ticket ya se crea con id interno (SEQ en la clase Ticket)
        data.getTickets().add(t);
    }

    public List<Ticket> listarTickets() {
        return data.getTickets();
    }

    public Ticket buscarTicketPorId(int id) {
        for (Ticket t : data.getTickets()) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    // ================== DICCIONARIO ==================

    public void guardarDiccionario(Diccionario d) {
        if (d.getId() == 0) {
            d.setId(contadorDiccionario++);
        }
        data.getDiccionarios().add(d);
    }

    public List<Diccionario> listarDiccionarios() {
        return data.getDiccionarios();
    }

    public Diccionario buscarDiccionarioPorId(int id) {
        for (Diccionario d : data.getDiccionarios()) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    public void agregarPalabraADiccionario(Diccionario dic, Palabra p) {
        dic.getPalabras().add(p);
    }

    public List<Palabra> listarPalabrasDeDiccionario(Diccionario dic) {
        return dic.getPalabras();
    }
}
