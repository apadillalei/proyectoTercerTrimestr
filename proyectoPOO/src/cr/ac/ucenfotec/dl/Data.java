package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.Departamento;
import cr.ac.ucenfotec.bl.entities.Diccionario;
import cr.ac.ucenfotec.bl.entities.Ticket;
import cr.ac.ucenfotec.bl.entities.Usuario;

import java.util.ArrayList;
import java.util.List;
/**
 * Capa de almacenamiento en memoria del sistema HelpDesk U.
 *
 * La clase {@code Data} funciona como un contenedor simple de listas donde se
 * almacenan las distintas entidades del dominio:
 * Usuarios, Departamentos, Tickets y Diccionarios.
 *
 * Esta clase no aplica reglas de negocio ni validaciones; únicamente gestiona
 * las colecciones, mientras que la lógica de manipulación corresponde al
 * {@link cr.ac.ucenfotec.bl.logic.Gestor}.
 *
 * En este avance, todos los datos se mantienen solo en memoria y se pierden
 * al finalizar la ejecución del programa.
 */

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
