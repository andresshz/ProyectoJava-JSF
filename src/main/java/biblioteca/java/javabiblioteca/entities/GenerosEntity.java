package biblioteca.java.javabiblioteca.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "generos", schema = "biblioteca_dwf")
@NamedQueries({
        @NamedQuery(name = "GenerosEntity.obtener", query = "SELECT e FROM GenerosEntity e"),
        @NamedQuery(name= "GenerosEntity.obtenerById", query = "SELECT e FROM GenerosEntity e where e.idGenero = :idGenero")
})

@Getter
@Setter
public class GenerosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_genero", nullable = false)
    private int idGenero;
    @Basic
    @Column(name = "nombre_genero", nullable = false, length = 50)
    private String nombreGenero;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenerosEntity entity = (GenerosEntity) o;

        if (idGenero != entity.idGenero) return false;
        if (nombreGenero != null ? !nombreGenero.equals(entity.nombreGenero) : entity.nombreGenero != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idGenero;
        result = 31 * result + (nombreGenero != null ? nombreGenero.hashCode() : 0);
        return result;
    }
}
