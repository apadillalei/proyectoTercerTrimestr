package cr.ac.ucenfotec.bl.entities;

/**
 * Representa un ticket de soporte dentro del sistema. Cada ticket contiene
 * un asunto, una descripción, un estado y referencias al usuario que lo
 * reporta y al departamento encargado de atenderlo.
 */
public class Ticket {

    /** Contador estático utilizado para asignar identificadores incrementales. */
    private static int SEQ = 1;

    /** Identificador único del ticket. */
    private int id;

    /** Asunto del ticket. */
    private String asunto;

    /** Descripción detallada de la incidencia o solicitud. */
    private String descripcion;

    /** Estado actual del ticket. */
    private String estado;

    /** Usuario que generó el ticket. */
    private Usuario usuario;

    /** Departamento asignado para atender el ticket. */
    private Departamento departamento;

    /**
     * Crea una nueva instancia de {@code Ticket}. El identificador se asigna
     * automáticamente utilizando un contador estático.
     *
     * @param asunto       asunto del ticket
     * @param descripcion  descripción de la incidencia o solicitud
     * @param estado       estado inicial del ticket
     * @param usuario      usuario que reporta el ticket
     * @param departamento departamento encargado de atenderlo
     */
    public Ticket(String asunto, String descripcion, String estado,
                  Usuario usuario, Departamento departamento) {
        this.id = SEQ++;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = estado;
        this.usuario = usuario;
        this.departamento = departamento;
    }

    /**
     * Obtiene el identificador del ticket.
     *
     * @return id del ticket
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el asunto del ticket.
     *
     * @return asunto del ticket
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Obtiene la descripción del ticket.
     *
     * @return descripción del ticket
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el estado actual del ticket.
     *
     * @return estado del ticket
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Obtiene el usuario que generó el ticket.
     *
     * @return usuario asociado al ticket
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Obtiene el departamento asignado al ticket.
     *
     * @return departamento asignado
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     * Modifica el estado del ticket.
     *
     * @param estado nuevo estado del ticket
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Devuelve una representación textual del ticket.
     *
     * @return cadena que incluye id, asunto, estado, usuario y departamento
     */
    @Override
    public String toString() {
        return "Ticket{id=" + id +
                ", asunto='" + asunto + '\'' +
                ", estado='" + estado + '\'' +
                ", usuario=" + (usuario == null ? "-" : usuario.getCorreo()) +
                ", depto=" + (departamento == null ? "-" : departamento.getNombre()) +
                '}';
    }

    /**
     * Compara este ticket con otro objeto. Dos tickets se consideran iguales
     * si comparten el mismo identificador.
     *
     * @param obj objeto a comparar
     * @return {@code true} si ambos tickets tienen el mismo id; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ticket other = (Ticket) obj;
        return this.id == other.id;
    }

    /**
     * Calcula el código hash del ticket basado en su identificador.
     *
     * @return valor hash del ticket
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
