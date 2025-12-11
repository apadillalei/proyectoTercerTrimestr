package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.Diccionario;
import cr.ac.ucenfotec.bl.entities.Palabra;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementa el análisis Bag of Words (BoW) para:
 *  - Detectar estado de ánimo (diccionario emocional)
 *  - Sugerir categoría técnica (diccionario técnico)
 *
 * Trabaja sobre las entidades del proyecto:
 *  {@link Diccionario} y {@link Palabra}.
 */
public class AnalisisBow {

    private Diccionario tecnico;
    private Diccionario emocional;

    /**
     * Crea el analizador recibiendo los diccionarios
     * necesarios. El tipo de diccionario se define
     * por el campo {@code tipo} (ej: "emocional", "tecnico").
     *
     * @param tecnico   diccionario técnico
     * @param emocional diccionario emocional
     */
    public AnalisisBow(Diccionario tecnico, Diccionario emocional) {
        this.tecnico = tecnico;
        this.emocional = emocional;
    }

    // =========================
    //  PREPROCESAMIENTO
    // =========================

    /**
     * Normaliza un texto:
     *  - a minúsculas
     *  - sin tildes
     *  - sin signos raros
     */
    public String normalizar(String texto) {
        if (texto == null) return "";

        String s = texto.toLowerCase();
        // Quitar tildes
        s = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        // Quitar símbolos, dejar letras/números/espacios
        s = s.replaceAll("[^a-z0-9\\s]", " ");
        // Compactar espacios
        s = s.replaceAll("\\s+", " ").trim();

        return s;
    }

    /**
     * Separa el texto en palabras usando espacios.
     */
    public String[] tokenizar(String texto) {
        if (texto == null || texto.isEmpty()) return new String[0];
        return texto.split("\\s+");
    }

    /**
     * Elimina las stopwords usando la clase Stopwords.
     */
    public List<String> quitarStopwords(String[] tokens) {
        List<String> resultado = new ArrayList<>();
        if (tokens == null) return resultado;

        for (String t : tokens) {
            if (!Stopwords.esStopword(t)) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    /**
     * Construye el mapa de frecuencias (TF) de un texto.
     *
     * @param texto descripción del ticket
     * @return mapa palabra -> cantidad de apariciones
     */
    public Map<String, Integer> vectorizarTFMap(String texto) {
        Map<String, Integer> tf = new HashMap<>();

        String normal = normalizar(texto);
        String[] tokens = tokenizar(normal);
        List<String> filtrados = quitarStopwords(tokens);

        for (String palabra : filtrados) {
            if (tf.containsKey(palabra)) {
                tf.put(palabra, tf.get(palabra) + 1);
            } else {
                tf.put(palabra, 1);
            }
        }

        return tf;
    }

    // =========================
    //  BÚSQUEDA EN DICCIONARIOS
    // =========================

    /**
     * Busca la categoría asociada a una palabra dentro
     * de un diccionario (emocional o técnico).
     *
     * @param diccionario diccionario a usar
     * @param termino     palabra normalizada
     * @return categoría (ej: "Frustracion", "Impresoras") o null si no hay coincidencia
     */
    private String buscarEnDiccionario(Diccionario diccionario, String termino) {
        if (diccionario == null || diccionario.getPalabras() == null) return null;

        for (Palabra p : diccionario.getPalabras()) {
            if (p.getTexto() != null &&
                    p.getTexto().equalsIgnoreCase(termino)) {
                return p.getCategoria();
            }
        }
        return null;
    }

    // =========================
    //  ESTADO DE ÁNIMO
    // =========================

    /**
     * Detecta el estado de ánimo predominante en la descripción
     * usando el diccionario emocional.
     *
     * Ejemplo de categorías: "Frustracion", "Urgencia", "Positivo".
     *
     * @param descripcion texto del ticket
     * @return emoción predominante o "Neutralidad" si no hay coincidencias
     */
    public String detectarEstadoAnimo(String descripcion) {
        if (descripcion == null || descripcion.isBlank() || emocional == null) {
            return "Neutralidad";
        }

        Map<String, Integer> tf = vectorizarTFMap(descripcion);
        // emoción -> puntaje
        Map<String, Integer> puntaje = new HashMap<>();

        for (Map.Entry<String, Integer> entry : tf.entrySet()) {
            String palabra = entry.getKey();
            int count = entry.getValue();

            String emocion = buscarEnDiccionario(emocional, palabra);
            if (emocion != null) {
                if (puntaje.containsKey(emocion)) {
                    puntaje.put(emocion, puntaje.get(emocion) + count);
                } else {
                    puntaje.put(emocion, count);
                }
            }
        }

        // Buscar la emoción con mayor puntaje
        String mejorEmocion = "Neutralidad";
        int mejorPuntaje = 0;

        for (Map.Entry<String, Integer> e : puntaje.entrySet()) {
            if (e.getValue() > mejorPuntaje) {
                mejorPuntaje = e.getValue();
                mejorEmocion = e.getKey();
            }
        }

        return mejorEmocion;
    }

    // =========================
    //  CATEGORÍA TÉCNICA
    // =========================

    /**
     * Sugiere la categoría técnica predominante según el
     * diccionario técnico.
     *
     * Ejemplos de categorías: "Impresoras", "Redes", "Cuentas".
     *
     * @param descripcion texto del ticket
     * @return categoría técnica sugerida o "General" si no hay coincidencias
     */
    public String sugerirCategoriaTecnica(String descripcion) {
        if (descripcion == null || descripcion.isBlank() || tecnico == null) {
            return "General";
        }

        Map<String, Integer> tf = vectorizarTFMap(descripcion);
        Map<String, Integer> puntaje = new HashMap<>();

        for (Map.Entry<String, Integer> entry : tf.entrySet()) {
            String palabra = entry.getKey();
            int count = entry.getValue();

            String categoria = buscarEnDiccionario(tecnico, palabra);
            if (categoria != null) {
                if (puntaje.containsKey(categoria)) {
                    puntaje.put(categoria, puntaje.get(categoria) + count);
                } else {
                    puntaje.put(categoria, count);
                }
            }
        }

        String mejorCategoria = "General";
        int mejorPuntaje = 0;

        for (Map.Entry<String, Integer> e : puntaje.entrySet()) {
            if (e.getValue() > mejorPuntaje) {
                mejorPuntaje = e.getValue();
                mejorCategoria = e.getKey();
            }
        }

        return mejorCategoria;
    }
}
