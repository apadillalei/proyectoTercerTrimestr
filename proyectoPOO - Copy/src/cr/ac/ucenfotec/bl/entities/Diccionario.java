package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;
import java.util.List;

public class Diccionario extends EntidadBase {

    private String tipo;
    private List<Palabra> palabras;

    public Diccionario(String tipo) {
        this.tipo = tipo;
        this.palabras = new ArrayList<>();
    }

    public Diccionario(){}

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Palabra> getPalabras() {
        return palabras;  // lista modificable, usada por Gestor
    }

    public void setPalabras(List<Palabra> palabras) {
        this.palabras = palabras;
    }

    @Override
    public String toString() {
        return "[" + id + "] Diccionario tipo='" + tipo + "', palabras=" + palabras.size();
    }
}
