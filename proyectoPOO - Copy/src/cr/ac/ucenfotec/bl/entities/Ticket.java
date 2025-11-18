package cr.ac.ucenfotec.bl.entities;

public class Ticket extends EntidadBase {
    private static int SEQ = 1;

    private String asunto;
    private String descripcion;
    private String estado;
    private Usuario usuario;
    private Departamento departamento;

    // Constructor
    public Ticket(String asunto, String descripcion, String estado,
                  Usuario usuario, Departamento departamento) {
        this.id = SEQ++;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = estado;
        this.usuario = usuario;
        this.departamento = departamento;
    }

    public Ticket(){}

    public int getId() { return id; }
    public String getAsunto() { return asunto; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }
    public Usuario getUsuario() { return usuario; }
    public Departamento getDepartamento() { return departamento; }


    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Ticket{id=" + id +
                ", asunto='" + asunto + '\'' +
                ", estado='" + estado + '\'' +
                ", usuario=" + (usuario==null? "-" : usuario.getCorreo()) +
                ", depto=" + (departamento==null? "-" : departamento.getNombre()) +
                '}';
    }
}
