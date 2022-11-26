package biblioteca.java.javabiblioteca.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estado_prestamo", schema = "biblioteca_dwf", catalog = "")

@Getter
@Setter
public class EstadoPrestamoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_estado_prestamo", nullable = false)
    private int idEstadoPrestamo;
    @Basic
    @Column(name = "estado_prestamo", nullable = false, length = 50)
    private String estadoPrestamo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstadoPrestamoEntity that = (EstadoPrestamoEntity) o;

        if (idEstadoPrestamo != that.idEstadoPrestamo) return false;
        if (estadoPrestamo != null ? !estadoPrestamo.equals(that.estadoPrestamo) : that.estadoPrestamo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEstadoPrestamo;
        result = 31 * result + (estadoPrestamo != null ? estadoPrestamo.hashCode() : 0);
        return result;
    }
}
