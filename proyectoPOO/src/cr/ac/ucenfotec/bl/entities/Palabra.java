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
    public String toString() {
        return texto + " (" + categoria + ")";
    }
}
