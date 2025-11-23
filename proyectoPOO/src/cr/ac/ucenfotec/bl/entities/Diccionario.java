package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un diccionario utilizado por el sistema HelpDesk U para
 * clasificar palabras según un tipo específico, como por ejemplo:
 * <ul>
 *     <li>Diccionario emocional</li>
 *     <li>Diccionario técnico</li>
 * </ul>
 *
 * <p>
 * Cada diccionario contiene una lista de palabras ({@link Palabra})
 * pertenecientes únicamente a ese diccionario. Este diseño permite
 * que más adelante se aplique el análisis Bag of Words (BoW),
 * donde los diccionarios servirán como base para interpretar
 * el contenido textual de los tickets.
 * </p>
 */
public class Diccionario {

    /** Identificador único del diccionario. */
    private int id;

    /** Tipo del diccionario (emocional, técnico, etc.). */
    private String tipo;

    /** Lista de palabras que pertenecen a este diccionario. */
    private List<Palabra> palabras;

    /**
     * Crea un diccionario de un tipo específico.
     * <p>
     * La lista interna de palabras se inicializa vacía.
     * </p>
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
     * @return id del diccionario
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna un identificador único al diccionario.
     * <p>
     * Este método es utilizado por la capa de lógica de negocio
     * al momento de registrar un diccionario en memoria.
     * </p>
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo del diccionario.
     *
     * @return tipo del diccionario
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Modifica el tipo del diccionario.
     *
     * @param tipo nuevo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la lista de palabras del diccionario.
     *
     * @return lista de palabras
     */
    public List<Palabra> getPalabras() {
        return palabras;
    }

    /**
     * Reemplaza la lista completa de palabras.
     *
     * @param palabras nueva lista de palabras
     */
    public void setPalabras(List<Palabra> palabras) {
        this.palabras = palabras;
    }

    /**
     * Devuelve una representación textual del diccionario,
     * mostrando su id, tipo y cantidad de palabras contenidas.
     *
     * @return representación legible del diccionario
     */
    @Override
    public String toString() {
        return "[" + id + "] Diccionario tipo='" + tipo + "', palabras=" + palabras.size();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Diccionario other = (Diccionario) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
