package cr.ac.ucenfotec.bl.entities;

/**
 * Representa un usuario dentro del sistema HelpDesk U.
 * <p>
 * Un usuario puede generar tickets, iniciar sesión y estar asociado a un rol
 * dentro de la institución (por ejemplo: estudiante, administrativo o soporte técnico).
 * Esta clase modela únicamente los datos básicos del usuario.
 * </p>
 */
public class Usuario {

    /** Identificador único del usuario dentro del sistema. */
    private int id;
    /** Nombre completo del usuario. */
    private String nombre;
    /** Correo electrónico institucional o de contacto. */
    private String correo;
    /** Contraseña de acceso al sistema (en este avance, sin cifrar). */
    private String password;
    /** Número de teléfono del usuario. */
    private String telefono;
    /** Rol del usuario dentro del sistema (Estudiante, Admin, Soporte, etc.). */
    private String rol;

    /**
     * Constructor vacío requerido para compatibilidad y para posibles usos
     * futuros de frameworks o serialización.
     */
    public Usuario() {
    }

    /**
     * Constructor para crear un usuario sin id asignado.
     * <p>
     * El id será asignado por la capa de lógica ({@code Gestor}) al momento
     * de registrarlo en el sistema.
     * </p>
     *
     * @param nombre   nombre completo del usuario
     * @param correo   correo electrónico
     * @param password contraseña de acceso
     * @param telefono número de teléfono
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
     * Constructor para crear un usuario con id ya definido.
     *
     * @param id       identificador único del usuario
     * @param nombre   nombre completo
     * @param correo   correo electrónico
     * @param password contraseña
     * @param telefono número telefónico
     * @param rol      rol dentro del sistema
     */
    public Usuario(int id, String nombre, String correo, String password, String telefono, String rol) {
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
     * <p>
     * Este método debería ser usado únicamente por la capa de lógica
     * (por ejemplo, {@code Gestor}).
     * </p>
     *
     * @param id nuevo identificador del usuario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return nombre completo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del usuario.
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
     * Obtiene la contraseña actual del usuario.
     *
     * @return contraseña en texto plano
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
     * @return teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Modifica el número telefónico del usuario.
     *
     * @param telefono nuevo teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el rol del usuario dentro del sistema.
     *
     * @return rol actual
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
     * Devuelve una representación textual del usuario,
     * útil para listados en consola.
     *
     * @return cadena con id, nombre, correo y rol.
     */
    @Override
    public String toString() {
        return "Usuario{id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario other = (Usuario) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
