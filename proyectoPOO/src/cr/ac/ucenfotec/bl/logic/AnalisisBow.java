package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.Diccionario;
import cr.ac.ucenfotec.bl.entities.Palabra;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase de apoyo para análisis de texto mediante un enfoque Bag of Words (BoW)
 * utilizando diccionarios técnicos y emocionales.
 *
 * <p>Permite normalizar texto, generar mapas de frecuencias y sugerir
 * estado de ánimo y categoría técnica a partir de los diccionarios
 * configurados.</p>
 */
public class AnalisisBow {

    /** Diccionario técnico utilizado para sugerir categorías técnicas. */
    private Diccionario tecnico;

    /** Diccionario emocional utilizado para estimar estado de ánimo. */
    private Diccionario emocional;

    /**
     * Crea una nueva instancia de {@code AnalisisBow} con los diccionarios
     * necesarios para el análisis.
     *
     * @param tecnico   diccionario técnico
     * @param emocional diccionario emocional
     */
    public AnalisisBow(Diccionario tecnico, Diccionario emocional) {
        this.tecnico = tecnico;
        this.emocional = emocional;
    }

    /**
     * Normaliza un texto aplicando:
     * <ul>
     *     <li>Conversión a minúsculas</li>
     *     <li>Eliminación de tildes</li>
     *     <li>Eliminación de símbolos no alfanuméricos</li>
     *     <li>Compactación de espacios</li>
     * </ul>
     *
     * @param texto texto original
     * @return texto normalizado o cadena vacía si el texto es {@code null}
     */
    public String normalizar(String texto) {
        if (texto == null) return "";

        String s = texto.toLowerCase();
        s = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        s = s.replaceAll("[^a-z0-9\\s]", " ");
        s = s.replaceAll("\\s+", " ").trim();

        return s;
    }

    /**
     * Divide el texto en tokens separados por espacios.
     *
     * @param texto texto a tokenizar
     * @return arreglo de tokens o arreglo vacío si el texto es nulo o vacío
     */
    public String[] tokenizar(String texto) {
        if (texto == null || texto.isEmpty()) return new String[0];
        return texto.split("\\s+");
    }

    /**
     * Elimina las palabras vacías (stopwords) de un arreglo de tokens.
     *
     * @param tokens arreglo de palabras
     * @return lista de tokens filtrados
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
     * Construye un mapa de frecuencias (TF) a partir de un texto.
     *
     * @param texto texto a procesar
     * @return mapa palabra -&gt; cantidad de apariciones
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

    /**
     * Convierte un mapa de frecuencias en una representación textual
     * con el formato {@code palabra:frecuencia}, separados por coma.
     *
     * @param tf mapa de frecuencias
     * @return representación textual del mapa o cadena vacía si el mapa está vacío
     */
    public String tfMapToString(Map<String, Integer> tf) {
        if (tf == null || tf.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> e : tf.entrySet()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(e.getKey()).append(":").append(e.getValue());
        }
        return sb.toString();
    }

    /**
     * Busca la categoría asociada a un término dentro de un diccionario.
     *
     * @param diccionario diccionario a consultar
     * @param termino     término normalizado a buscar
     * @return categoría asociada o {@code null} si no hay coincidencias
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

    /**
     * Determina el estado de ánimo predominante en una descripción utilizando
     * el diccionario emocional.
     *
     * @param descripcion texto a analizar
     * @return emoción predominante o {@code "Neutralidad"} si no hay coincidencias
     */
    public String detectarEstadoAnimo(String descripcion) {
        if (descripcion == null || descripcion.isBlank() || emocional == null) {
            return "Neutralidad";
        }

        Map<String, Integer> tf = vectorizarTFMap(descripcion);
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

    /**
     * Sugiere la categoría técnica predominante en una descripción utilizando
     * el diccionario técnico.
     *
     * @param descripcion texto a analizar
     * @return categoría técnica sugerida o {@code "General"} si no hay coincidencias
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
