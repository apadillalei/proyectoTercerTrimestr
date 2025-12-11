package cr.ac.ucenfotec.bl.entities;

/**
 * Representa una palabra almacenada dentro de un diccionario del sistema.
 * Cada palabra cuenta con un texto y una categoría asociada.
 */
public class Palabra {

    /** Texto literal de la palabra. */
    private String texto;

    /** Categoría o tipo de la palabra. */
    private String categoria;

    /**
     * Crea una nueva instancia de {@code Palabra} con su texto y categoría.
     *
     * @param texto     texto literal de la palabra
     * @param categoria categoría asignada
     */
    public Palabra(String texto, String categoria) {
        this.texto = texto;
        this.categoria = categoria;
    }

    /**
     * Constructor vacío para compatibilidad.
     */
    public Palabra() {}

    /**
     * Obtiene el texto de la palabra.
     *
     * @return texto literal de la palabra
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Obtiene la categoría asociada a la palabra.
     *
     * @return categoría de la palabra
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Devuelve una representación textual de la palabra en el formato:
     * {@code texto (categoría)}.
     *
     * @return representación textual de la palabra
     */
    @Override
    public String toString() {
        return texto + " (" + categoria + ")";
    }

    /**
     * Compara esta palabra con otra. Dos palabras se consideran iguales
     * si su texto y categoría coinciden, sin distinguir mayúsculas.
     *
     * @param obj objeto a comparar
     * @return {@code true} si ambas palabras son equivalentes; {@code false} de lo contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Palabra other = (Palabra) obj;

        return texto != null && texto.equalsIgnoreCase(other.texto)
                && categoria != null && categoria.equalsIgnoreCase(other.categoria);
    }

    /**
     * Calcula el código hash basado en el texto y la categoría en minúsculas.
     *
     * @return valor hash de la palabra
     */
    @Override
    public int hashCode() {
        int result = (texto == null) ? 0 : texto.toLowerCase().hashCode();
        result = 31 * result + ((categoria == null) ? 0 : categoria.toLowerCase().hashCode());
        return result;
    }
}
