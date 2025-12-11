package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un diccionario utilizado para clasificar palabras según un tipo
 * específico dentro del sistema. Cada diccionario agrupa palabras que
 * pertenecen a una misma categoría (por ejemplo, emocional o técnico).
 */
public class Diccionario {

    /** Identificador único del diccionario. */
    private int id;

    /** Tipo del diccionario (emocional, técnico, etc.). */
    private String tipo;

    /** Lista de palabras asociadas a este diccionario. */
    private List<Palabra> palabras;

    /**
     * Crea una nueva instancia de {@code Diccionario} con un tipo determinado.
     * La lista interna de palabras se inicializa vacía.
     *
     * @param tipo tipo del diccionario
     */
    public Diccionario(String tipo) {
        this.tipo = tipo;
        this.palabras = new ArrayList<>();
    }

    /**
     * Obtiene el identificador del diccionario.
     *
     * @return el id del diccionario
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del diccionario.
     *
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo del diccionario.
     *
     * @return el tipo del diccionario
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Modifica el tipo del diccionario.
     *
     * @param tipo nuevo tipo del diccionario
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la lista de palabras asociadas a este diccionario.
     *
     * @return lista de palabras
     */
    public List<Palabra> getPalabras() {
        return palabras;
    }

    /**
     * Reemplaza la lista completa de palabras del diccionario.
     *
     * @param palabras nueva lista de palabras
     */
    public void setPalabras(List<Palabra> palabras) {
        this.palabras = palabras;
    }

    /**
     * Devuelve una representación textual del diccionario.
     *
     * @return cadena que incluye id, tipo y cantidad de palabras
     */
    @Override
    public String toString() {
        return "[" + id + "] Diccionario tipo='" + tipo + "', palabras=" + palabras.size();
    }

    /**
     * Compara este diccionario con otro objeto.
     * Dos diccionarios son iguales si comparten el mismo id.
     *
     * @param obj objeto a comparar
     * @return {@code true} si ambos tienen el mismo id; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Diccionario other = (Diccionario) obj;
        return this.id == other.id;
    }

    /**
     * Calcula el código hash del diccionario basado en su identificador.
     *
     * @return valor hash del diccionario
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
