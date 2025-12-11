package cr.ac.ucenfotec.bl.logic;

/**
 * Lista de stopwords (palabras vacías) en español
 * usadas por el análisis Bag of Words.
 */
public class Stopwords {

    // Arreglo básico, sin Set ni cosas raras
    private static final String[] DEFAULT = {
            "a","al","algo","algunas","algunos","ante","antes","como","con","contra",
            "cual","cuando","de","del","desde","donde","el","ella","ellas","ellos",
            "en","entre","era","es","esta","estaba","estan","estoy","etc","ha","han",
            "hasta","la","las","le","les","lo","los","me","mi","mis","muy","no",
            "nos","o","os","para","pero","por","que","si","sin","sobre","su","sus",
            "te","tu","tus","un","una","unas","unos","y","ya"
    };

    /**
     * Devuelve el arreglo de stopwords por defecto.
     */
    public static String[] getDefault() {
        return DEFAULT;
    }

    /**
     * Indica si una palabra está en la lista de stopwords.
     *
     * @param palabra palabra normalizada
     * @return true si es stopword, false en caso contrario
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
