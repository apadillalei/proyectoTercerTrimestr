package cr.ac.ucenfotec.bl.entities;

/**
 * Representa un ticket de soporte dentro del sistema HelpDesk U.
 * <p>
 * Cada ticket contiene un asunto, una descripción, un estado y referencias
 * al usuario que reporta la incidencia y al departamento encargado de atenderla.
 * El identificador del ticket se asigna automáticamente mediante un contador
 * estático en memoria.
 * </p>
 */
public class Ticket {

    /** Contador estático para asignar IDs incrementales a cada ticket. */
    private static int SEQ = 1;

    /** Identificador único del ticket. */
    private int id;
    /** Asunto o título breve del ticket. */
    private String asunto;
    /** Descripción detallada de la incidencia o solicitud. */
    private String descripcion;
    /** Estado actual del ticket (por ejemplo: Nuevo, En proceso, Cerrado). */
    private String estado;
    /** Usuario que generó el ticket. */
    private Usuario usuario;
    /** Departamento responsable de atender el ticket. */
    private Departamento departamento;

    /**
     * Constructor principal del ticket.
     * <p>
     * El identificador se asigna automáticamente usando el contador estático
     * {@code SEQ}, simulando una llave primaria autoincremental en memoria.
     * </p>
     *
     * @param asunto       asunto del ticket
     * @param descripcion  descripción del problema o solicitud
     * @param estado       estado inicial del ticket
     * @param usuario      usuario que reporta la incidencia
     * @param departamento departamento asignado
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
     * Obtiene el identificador único del ticket.
     *
     * @return id del ticket
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el asunto del ticket.
     *
     * @return asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Obtiene la descripción del ticket.
     *
     * @return descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el estado actual del ticket.
     *
     * @return estado (Nuevo, En proceso, Cerrado, etc.)
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Obtiene el usuario que generó el ticket.
     *
     * @return usuario asociado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Obtiene el departamento asignado al ticket.
     *
     * @return departamento asociado
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
     * Devuelve una representación textual del ticket para su uso en listados de consola.
     *
     * @return cadena con id, asunto, estado, correo del usuario y nombre del departamento.
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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ticket other = (Ticket) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
