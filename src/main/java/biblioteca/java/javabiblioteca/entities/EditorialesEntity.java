package biblioteca.java.javabiblioteca.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "editoriales", schema = "biblioteca_dwf")
@NamedQueries({
        @NamedQuery(name = "EditorialesEntity.findAll", query = "SELECT e FROM EditorialesEntity e"),
        @NamedQuery(name = "EditorialesEntity.findByIdEditorial", query = "SELECT e FROM EditorialesEntity e WHERE e.idEditorial= :idEditorial")
})

@Getter
@Setter
public class EditorialesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_editorial", nullable = false)
    private int idEditorial;
    @Basic
    @Column(name = "nombre_editorial", nullable = false, length = 50)
    private String nombreEditorial;
    @Basic
    @Column(name = "contacto", nullable = false, length = 50)
    private String contacto;
    @Basic
    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;
    @Basic
    @Column(name = "direccion", nullable = false, length = 100)
    private String direccion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EditorialesEntity that = (EditorialesEntity) o;

        if (idEditorial != that.idEditorial) return false;
        if (nombreEditorial != null ? !nombreEditorial.equals(that.nombreEditorial) : that.nombreEditorial != null)
            return false;
        if (contacto != null ? !contacto.equals(that.contacto) : that.contacto != null) return false;
        if (telefono != null ? !telefono.equals(that.telefono) : that.telefono != null) return false;
        if (direccion != null ? !direccion.equals(that.direccion) : that.direccion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEditorial;
        result = 31 * result + (nombreEditorial != null ? nombreEditorial.hashCode() : 0);
        result = 31 * result + (contacto != null ? contacto.hashCode() : 0);
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (direccion != null ? direccion.hashCode() : 0);
        return result;
    }
}
