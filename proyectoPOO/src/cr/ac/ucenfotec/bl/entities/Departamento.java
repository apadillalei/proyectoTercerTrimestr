package cr.ac.ucenfotec.bl.entities;

public class Departamento {

    private int id;
    private String nombre;
    private String descripcion;
    private String correoContacto;

    public Departamento(String nombre, String descripcion, String correoContacto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.correoContacto = correoContacto;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " - " + correoContacto;
    }
}
