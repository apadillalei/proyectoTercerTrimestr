package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.Departamento;
import cr.ac.ucenfotec.bl.entities.Diccionario;
import cr.ac.ucenfotec.bl.entities.Ticket;
import cr.ac.ucenfotec.bl.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private List<Usuario> usuarios;
    private List<Departamento> departamentos;
    private List<Ticket> tickets;
    private List<Diccionario> diccionarios;

    public Data() {
        usuarios = new ArrayList<>();
        departamentos = new ArrayList<>();
        tickets = new ArrayList<>();
        diccionarios = new ArrayList<>();
    }



    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<Diccionario> getDiccionarios() {
        return diccionarios;
    }
}
