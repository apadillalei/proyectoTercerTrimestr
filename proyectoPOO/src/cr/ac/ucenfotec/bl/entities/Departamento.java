package cr.ac.ucenfotec.bl.entities;

/**
 * Representa un departamento dentro de la institución.
 * Un departamento organiza y gestiona tickets según su área de atención.
 *
 * <p>Incluye información básica como nombre, descripción y un correo de contacto.
 * El identificador ({@code id}) es asignado por la capa de lógica.</p>
 */
public class Departamento {

    /** Identificador único del departamento. */
    private int id;

    /** Nombre del departamento. */
    private String nombre;

    /** Descripción general del departamento. */
    private String descripcion;

    /** Correo electrónico de contacto del departamento. */
    private String correoContacto;

    /**
     * Crea una nueva instancia de {@code Departamento} con la información principal.
     *
     * @param nombre          nombre del departamento
     * @param descripcion     descripción del departamento
     * @param correoContacto  correo electrónico del departamento
     */
    public Departamento(String nombre, String descripcion, String correoContacto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.correoContacto = correoContacto;
    }

    /**
     * Obtiene el identificador único del departamento.
     *
     * @return el id del departamento
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del departamento.
     *
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del departamento.
     *
     * @return el nombre del departamento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del departamento.
     *
     * @param nombre nuevo nombre del departamento
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del departamento.
     *
     * @return la descripción del departamento
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripción del departamento.
     *
     * @param descripcion nueva descripción del departamento
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el correo electrónico de contacto del departamento.
     *
     * @return correo electrónico del departamento
     */
    public String getCorreoContacto() {
        return correoContacto;
    }

    /**
     * Modifica el correo electrónico de contacto del departamento.
     *
     * @param correoContacto nuevo correo de contacto
     */
    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    /**
     * Devuelve una representación textual del departamento.
     *
     * @return una cadena que incluye id, nombre y correo de contacto
     */
    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " - " + correoContacto;
    }

    /**
     * Compara si dos departamentos son iguales según su identificador.
     *
     * @param obj objeto con el que se compara
     * @return {@code true} si ambos objetos representan el mismo departamento; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Departamento other = (Departamento) obj;
        return this.id == other.id;
    }

    /**
     * Calcula el código hash del departamento basado en su identificador.
     *
     * @return valor hash del departamento
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
