package cr.ac.ucenfotec.bl.entities;

/**
 * Representa un departamento dentro de la institución,
 * encargado de atender tickets según su área de especialización.
 * <p>
 * Ejemplos de departamentos podrían ser:
 * <ul>
 *     <li>Soporte Técnico</li>
 *     <li>Vida Estudiantil</li>
 *     <li>Financiero</li>
 * </ul>
 * </p>
 *
 * <p>
 * Cada departamento almacena información administrativa relevante,
 * incluyendo su nombre, una breve descripción y un correo de contacto.
 * Los usuarios del sistema pueden asignar tickets a un departamento
 * apropiado según el tipo de incidencia.
 * </p>
 */
public class Departamento {

    /** Identificador único del departamento. */
    private int id;

    /** Nombre del departamento. */
    private String nombre;

    /** Descripción general del departamento. */
    private String descripcion;

    /** Correo electrónico utilizado para consultas o soporte. */
    private String correoContacto;

    /**
     * Crea un departamento con su información principal.
     *
     * @param nombre nombre del departamento
     * @param descripcion descripción del departamento
     * @param correoContacto correo de contacto del departamento
     */
    public Departamento(String nombre, String descripcion, String correoContacto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.correoContacto = correoContacto;
    }

    /**
     * Obtiene el identificador del departamento.
     *
     * @return id del departamento
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna un identificador al departamento.
     * <p>
     * Este valor es gestionado por la capa de lógica ({@code Gestor}).
     * </p>
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del departamento.
     *
     * @return nombre del departamento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del departamento.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del departamento.
     *
     * @return descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripción del departamento.
     *
     * @param descripcion nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el correo de contacto del departamento.
     *
     * @return correo electrónico
     */
    public String getCorreoContacto() {
        return correoContacto;
    }

    /**
     * Modifica el correo de contacto del departamento.
     *
     * @param correoContacto nuevo correo
     */
    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    /**
     * Representación textual del departamento,
     * mostrando id, nombre y correo de contacto.
     *
     * @return cadena representativa del departamento
     */
    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " - " + correoContacto;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Departamento other = (Departamento) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
