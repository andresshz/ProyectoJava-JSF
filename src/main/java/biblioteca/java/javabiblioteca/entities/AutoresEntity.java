package biblioteca.java.javabiblioteca.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "autores", schema = "biblioteca_dwf")
@NamedQueries({
        @NamedQuery(name = "AutoresEntity.findAll", query = "SELECT a FROM AutoresEntity a"),
        @NamedQuery(name= "AutoresEntity.obtenerById", query = "SELECT e FROM AutoresEntity e where e.idAutor = :idAutor")
})

@Getter
@Setter
public class AutoresEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_autor", nullable = false)
    private int idAutor;
    @Basic
    @Column(name = "nombres", nullable = false, length = 50)
    private String nombres;
    @Basic
    @Column(name = "apellidos", nullable = false, length = 50)
    private String apellidos;
    @Basic
    @Column(name = "nacionalidad", nullable = false, length = 50)
    private String nacionalidad;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AutoresEntity that = (AutoresEntity) o;

        if (idAutor != that.idAutor) return false;
        if (nombres != null ? !nombres.equals(that.nombres) : that.nombres != null) return false;
        if (apellidos != null ? !apellidos.equals(that.apellidos) : that.apellidos != null) return false;
        if (nacionalidad != null ? !nacionalidad.equals(that.nacionalidad) : that.nacionalidad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAutor;
        result = 31 * result + (nombres != null ? nombres.hashCode() : 0);
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (nacionalidad != null ? nacionalidad.hashCode() : 0);
        return result;
    }
}
