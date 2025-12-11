package cr.ac.ucenfotec.bl.logic;

/**
 * Proporciona una lista básica de palabras vacías (stopwords) en español,
 * utilizadas durante el análisis de texto para el enfoque Bag of Words.
 */
public class Stopwords {

    /** Arreglo de stopwords por defecto. */
    private static final String[] DEFAULT = {
            "a","al","algo","algunas","algunos","ante","antes","como","con","contra",
            "cual","cuando","de","del","desde","donde","el","ella","ellas","ellos",
            "en","entre","era","es","esta","estaba","estan","estoy","etc","ha","han",
            "hasta","la","las","le","les","lo","los","me","mi","mis","muy","no",
            "nos","o","os","para","pero","por","que","si","sin","sobre","su","sus",
            "te","tu","tus","un","una","unas","unos","y","ya"
    };

    /**
     * Obtiene el arreglo de stopwords por defecto.
     *
     * @return arreglo con las palabras vacías
     */
    public static String[] getDefault() {
        return DEFAULT;
    }

    /**
     * Determina si una palabra pertenece a la lista de stopwords.
     *
     * @param palabra palabra previamente normalizada
     * @return {@code true} si la palabra es una stopword; {@code false} en caso contrario
     */
    public static boolean esStopword(String palabra) {
        if (palabra == null || palabra.isEmpty()) return false;
        for (String s : DEFAULT) {
            if (s.equals(palabra)) {
                return true;
            }
        }
        return false;
    }
}
