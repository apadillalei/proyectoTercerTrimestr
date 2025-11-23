package cr.ac.ucenfotec.bl.entities;

/**
 * Representa una palabra registrada dentro de un diccionario
 * del sistema HelpDesk U.
 * <p>
 * Cada palabra tiene un texto y una categoría asociada
 * (por ejemplo, una emoción o un concepto técnico).
 * Estas palabras serán utilizadas en futuros avances para
 * el análisis Bag of Words (BoW).
 * </p>
 */
public class Palabra {

    /** Texto literal de la palabra. */
    private String texto;
    /** Categoría o tipo de la palabra (emocional, técnica, etc.). */
    private String categoria;

    /**
     * Crea una nueva palabra con texto y categoría.
     *
     * @param texto     texto de la palabra
     * @param categoria categoría o tipo asociado
     */
    public Palabra(String texto, String categoria) {
        this.texto = texto;
        this.categoria = categoria;
    }

    /**
     * Constructor vacío para compatibilidad y posibles usos futuros.
     */
    public Palabra(){}

    /**
     * Obtiene el texto de la palabra.
     *
     * @return texto literal
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Obtiene la categoría de la palabra.
     *
     * @return categoría asociada
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Devuelve una representación de la palabra en el formato:
     * {@code texto (categoría)}.
     *
     * @return representación legible de la palabra
     */
    @Override
    public String toString() {
        return texto + " (" + categoria + ")";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Palabra other = (Palabra) obj;

        return texto != null && texto.equalsIgnoreCase(other.texto)
                && categoria != null && categoria.equalsIgnoreCase(other.categoria);
    }

    @Override
    public int hashCode() {
        int result = (texto == null) ? 0 : texto.toLowerCase().hashCode();
        result = 31 * result + ((categoria == null) ? 0 : categoria.toLowerCase().hashCode());
        return result;
    }

}
