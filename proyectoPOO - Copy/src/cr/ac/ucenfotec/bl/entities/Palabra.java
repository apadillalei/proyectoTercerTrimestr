package cr.ac.ucenfotec.bl.entities;

import java.util.Objects;

public class Palabra {

    private String texto;
    private String categoria;

    public Palabra(String texto, String categoria) {
        this.texto = texto;
        this.categoria = categoria;
    }

    public Palabra(){}

    public String getTexto() {
        return texto;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Palabra palabra = (Palabra) o;
        return texto.equalsIgnoreCase(palabra.texto)
                && categoria.equalsIgnoreCase(palabra.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texto.toLowerCase(), categoria.toLowerCase());
    }

    @Override
    public String toString() {
        return texto + " (" + categoria + ")";
    }
}
