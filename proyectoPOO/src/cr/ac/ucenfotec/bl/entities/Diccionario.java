package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;
import java.util.List;

public class Diccionario {

    private int id;
    private String tipo;
    private List<Palabra> palabras;

    public Diccionario(String tipo) {
        this.tipo = tipo;
        this.palabras = new ArrayList<>();
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Palabra> getPalabras() {
        return palabras;
    }

    public void setPalabras(List<Palabra> palabras) {
        this.palabras = palabras;
    }

    @Override
    public String toString() {
        return "[" + id + "] Diccionario tipo='" + tipo + "', palabras=" + palabras.size();
    }
}
