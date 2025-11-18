package cr.ac.ucenfotec.bl.entities;

import java.util.Objects;

public abstract class EntidadBase {

    protected int id;

    public EntidadBase() {
    }

    public EntidadBase(int id) {
        this.id = id;
    }

    // Getter y setter de id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadBase that = (EntidadBase) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
