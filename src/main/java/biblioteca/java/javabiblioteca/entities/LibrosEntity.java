package biblioteca.java.javabiblioteca.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "libros", schema = "biblioteca_dwf")
@NamedQueries({
        @NamedQuery(name = "LibrosEntity.findAll", query = "SELECT l FROM LibrosEntity l"),
        @NamedQuery(name = "LibrosEntity.obtenerById", query = "SELECT l FROM LibrosEntity l WHERE l.idLibro = :idLibro"),
        @NamedQuery(name = "LibrosEntity.findHistorial", query = "SELECT p FRom PrestamosEntity p WHERE p.librosByIdLibro.idLibro = :idLibro ORDER BY p.fechaInicio ASC")
})

@Getter
@Setter
public class LibrosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_libro", nullable = false)
    private int idLibro;
    @Basic
    @Column(name = "codigo_libro", nullable = false, length = 9)
    private String codigoLibro;
    @Basic
    @Column(name = "titulo", nullable = false, length = 50)
    private String titulo;
    @Basic
    @Column(name = "fecha_edicion", nullable = true)
    private Date fechaEdicion;
    @Basic
    @Column(name = "isbn", nullable = false, length = 17)
    private String isbn;
    @Basic
    @Column(name = "paginas", nullable = false)
    private int paginas;
    @Basic
    @Column(name = "ejemplares", nullable = false)
    private int ejemplares;
    @Basic
    @Column(name = "descripcion", nullable = false, length = 150)
    private String descripcion;
    @Basic
    @Column(name = "imagen_libro", nullable = true, length = 100)
    private String imagenLibro;

    /*
     **
     * RELACIONES
     *
     */

    //Tabla autor
    @ManyToOne
    @JoinColumn(name = "id_autor", referencedColumnName = "id_autor", nullable = false)
    private AutoresEntity autoresByIdAutor;

    //Tabla editorial
    @ManyToOne
    @JoinColumn(name = "id_editorial", referencedColumnName = "id_editorial", nullable = false)
    private EditorialesEntity editorialesByIdEditorial;

    //Tabla genero
    @ManyToOne
    @JoinColumn(name = "id_genero", referencedColumnName = "id_genero", nullable = false)
    private GenerosEntity generosByIdGenero;

    //Tabla usuario bibliotecario
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private UsuariosEntity usuariosByIdUsuario;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibrosEntity that = (LibrosEntity) o;

        if (idLibro != that.idLibro) return false;
        if (paginas != that.paginas) return false;
        if (ejemplares != that.ejemplares) return false;
        if (codigoLibro != null ? !codigoLibro.equals(that.codigoLibro) : that.codigoLibro != null) return false;
        if (titulo != null ? !titulo.equals(that.titulo) : that.titulo != null) return false;
        if (fechaEdicion != null ? !fechaEdicion.equals(that.fechaEdicion) : that.fechaEdicion != null) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (imagenLibro != null ? !imagenLibro.equals(that.imagenLibro) : that.imagenLibro != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLibro;
        result = 31 * result + (codigoLibro != null ? codigoLibro.hashCode() : 0);
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (fechaEdicion != null ? fechaEdicion.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + paginas;
        result = 31 * result + ejemplares;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (imagenLibro != null ? imagenLibro.hashCode() : 0);
        return result;
    }
}
