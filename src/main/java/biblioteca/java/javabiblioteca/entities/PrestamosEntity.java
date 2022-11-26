package biblioteca.java.javabiblioteca.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "prestamos", schema = "biblioteca_dwf")
@NamedQueries({
        @NamedQuery(name = "PrestamosEntity.findAll", query = "select p from PrestamosEntity p"),
        @NamedQuery(name = "PrestamosEntity.findByUsuariosByIdUsuarioPrestador_IdUsuario", query = "select p from PrestamosEntity p where p.usuariosByIdUsuarioPrestador.idUsuario = :idUsuario"),
        @NamedQuery(name = "PrestamosEntity.obtenerAccionesPrestadas", query = "select p from PrestamosEntity p WHERE p.estadosByIdEstadoPrestamo.idEstadoPrestamo = 2 AND p.usuariosByIdUsuarioPrestador.idUsuario = :idUsuario"),
        @NamedQuery(name = "PrestamosEntity.obtenerAccionesCanceladas", query = "select p from PrestamosEntity p WHERE p.estadosByIdEstadoPrestamo.idEstadoPrestamo = 3 AND p.usuariosByIdUsuarioPrestador.idUsuario = :idUsuario"),
        @NamedQuery(name = "PrestamosEntity.obtenerAccionesDevueltas", query = "select p from PrestamosEntity p WHERE p.estadosByIdEstadoPrestamo.idEstadoPrestamo = 5 AND p.usuariosByIdUsuarioPrestador.idUsuario = :idUsuario"),
        @NamedQuery(name = "PrestamosEntity.obtenerHistorialPrestamos", query = "select p from PrestamosEntity p WHERE p.usuariosByIdUsuarioPrestatario.idUsuario = :idUsuario ORDER BY p.codigoPrestamo"),
        @NamedQuery(name = "PrestamosEntity.obtenerPrestamosByIdUsuario", query = "select p from PrestamosEntity p WHERE p.usuariosByIdUsuarioPrestatario.idUsuario = :idUsuario"),
        @NamedQuery(name = "PrestamosEntity.ultimoPrestamo", query = "select p from PrestamosEntity p order by p.idPrestamo desc"),
        @NamedQuery(name = "PrestamosEntity.obtenerPrestamoById", query = "select p from PrestamosEntity p WHERE p.idPrestamo = :idPrestamo"),
        @NamedQuery(name = "PrestamosEntity.findBySolicitado", query = "select p from PrestamosEntity p WHERE p.usuariosByIdUsuarioPrestador is null"),
        @NamedQuery(name = "PrestamosEntity.findByRetrasado", query = "select p from PrestamosEntity p WHERE p.estadosByIdEstadoPrestamo.idEstadoPrestamo = 4"),
        @NamedQuery(name = "PrestamosEntity.findByCancelados", query = "select p from PrestamosEntity p WHERE p.estadosByIdEstadoPrestamo.idEstadoPrestamo = 3"),
        @NamedQuery(name = "PrestamosEntity.findByDevueltos", query = "select p from PrestamosEntity p WHERE p.estadosByIdEstadoPrestamo.idEstadoPrestamo = 5"),
        @NamedQuery(name = "PrestamosEntity.findByPrestados", query = "select p from PrestamosEntity p WHERE p.estadosByIdEstadoPrestamo.idEstadoPrestamo = 2"),
        @NamedQuery(name = "PrestamosEntity.findById", query = "select p from PrestamosEntity p WHERE p.idPrestamo = :idPrestamo")
})

@Getter
@Setter
public class PrestamosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_prestamo", nullable = false)
    private int idPrestamo;
    @Basic
    @Column(name = "codigo_prestamo", nullable = false, length = 8)
    private String codigoPrestamo;
    @Basic
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;
    @Basic
    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;
    @Basic
    @Column(name = "fecha_registro", nullable = false)
    private Timestamp fechaRegistro;

    /*
     **
     * RELACIONES
     *
     */

    @ManyToOne
    @JoinColumn(name = "id_usuario_prestador", referencedColumnName = "id_usuario", nullable = false)
    private UsuariosEntity usuariosByIdUsuarioPrestador;

    @ManyToOne
    @JoinColumn(name = "id_usuario_prestatario", referencedColumnName = "id_usuario", nullable = false)
    private UsuariosEntity usuariosByIdUsuarioPrestatario;

    @ManyToOne
    @JoinColumn(name = "id_estado_prestamo", referencedColumnName = "id_estado_prestamo", nullable = false)
    private EstadoPrestamoEntity estadosByIdEstadoPrestamo;

    @ManyToOne
    @JoinColumn(name = "id_libro", referencedColumnName = "id_libro", nullable = false)
    private LibrosEntity librosByIdLibro;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrestamosEntity that = (PrestamosEntity) o;

        if (idPrestamo != that.idPrestamo) return false;
        if (fechaInicio != null ? !fechaInicio.equals(that.fechaInicio) : that.fechaInicio != null) return false;
        if (fechaFin != null ? !fechaFin.equals(that.fechaFin) : that.fechaFin != null) return false;
        if (fechaRegistro != null ? !fechaRegistro.equals(that.fechaRegistro) : that.fechaRegistro != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPrestamo;
        result = 31 * result + (fechaInicio != null ? fechaInicio.hashCode() : 0);
        result = 31 * result + (fechaFin != null ? fechaFin.hashCode() : 0);
        result = 31 * result + (fechaRegistro != null ? fechaRegistro.hashCode() : 0);
        return result;
    }
}
