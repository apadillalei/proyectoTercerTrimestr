package cr.ac.ucenfotec.bl.entities;

/**
 * Representa un usuario dentro del sistema. Un usuario puede generar tickets
 * y posee información básica como nombre, correo, contraseña, teléfono y rol.
 */
public class Usuario {

    /** Identificador único del usuario dentro del sistema. */
    private int id;

    /** Nombre completo del usuario. */
    private String nombre;

    /** Correo electrónico del usuario. */
    private String correo;

    /** Contraseña de acceso al sistema. */
    private String password;

    /** Número telefónico del usuario. */
    private String telefono;

    /** Rol asignado dentro del sistema. */
    private String rol;

    /**
     * Constructor vacío para compatibilidad con procesos de serialización
     * u otros usos donde se requiera una instancia sin inicializar.
     */
    public Usuario() {
    }

    /**
     * Crea un usuario sin un id asignado. El identificador será definido por
     * la capa de lógica al momento del registro.
     *
     * @param nombre   nombre completo del usuario
     * @param correo   correo electrónico del usuario
     * @param password contraseña de acceso
     * @param telefono número telefónico
     * @param rol      rol dentro del sistema
     */
    public Usuario(String nombre, String correo, String password, String telefono, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.rol = rol;
    }

    /**
     * Crea un usuario con un identificador ya definido.
     *
     * @param id       identificador único del usuario
     * @param nombre   nombre completo del usuario
     * @param correo   correo electrónico
     * @param password contraseña de acceso
     * @param telefono número telefónico
     * @param rol      rol dentro del sistema
     */
    public Usuario(int id, String nombre, String correo, String password,
                   String telefono, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.rol = rol;
    }

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return id del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador único del usuario.
     *
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return nombre completo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre completo del usuario.
     *
     * @param nombre nuevo nombre completo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return correo electrónico
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Modifica el correo electrónico del usuario.
     *
     * @param correo nuevo correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Modifica la contraseña del usuario.
     *
     * @param password nueva contraseña
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el número telefónico del usuario.
     *
     * @return número telefónico
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Modifica el número telefónico del usuario.
     *
     * @param telefono nuevo número telefónico
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el rol del usuario dentro del sistema.
     *
     * @return rol del usuario
     */
    public String getRol() {
        return rol;
    }

    /**
     * Modifica el rol del usuario dentro del sistema.
     *
     * @param rol nuevo rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Devuelve una representación textual del usuario.
     *
     * @return cadena con id, nombre, correo y rol
     */
    @Override
    public String toString() {
        return "Usuario{id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }

    /**
     * Compara este usuario con otro objeto. Dos usuarios se consideran iguales
     * si comparten el mismo identificador.
     *
     * @param obj objeto a comparar
     * @return {@code true} si ambos usuarios tienen el mismo id; {@code false} de lo contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario other = (Usuario) obj;
        return this.id == other.id;
    }

    /**
     * Calcula el código hash del usuario basado en su identificador.
     *
     * @return valor hash del usuario
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
