package biblioteca.java.javabiblioteca.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cargo_usuarios", schema = "biblioteca_dwf")
@NamedQueries({
        @NamedQuery(name = "CargoUsuariosEntity.findAllCrud", query = "SELECT cu FROM CargoUsuariosEntity cu WHERE cu.idCargoUsuario = 1 OR cu.idCargoUsuario = 2 ORDER BY cu.idCargoUsuario"),
        @NamedQuery(name = "CargoUsuariosEntity.findAllRegistro", query = "SELECT cu FROM CargoUsuariosEntity cu WHERE cu.idCargoUsuario = 3 OR cu.idCargoUsuario = 4 OR cu.idCargoUsuario = 5 ORDER BY cu.idCargoUsuario")
})

@Getter
@Setter
public class CargoUsuariosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cargo_usuario", nullable = false)
    private int idCargoUsuario;
    @Basic
    @Column(name = "cargo_usuario", nullable = false, length = 50)
    private String cargoUsuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargoUsuariosEntity that = (CargoUsuariosEntity) o;

        if (idCargoUsuario != that.idCargoUsuario) return false;
        if (cargoUsuario != null ? !cargoUsuario.equals(that.cargoUsuario) : that.cargoUsuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCargoUsuario;
        result = 31 * result + (cargoUsuario != null ? cargoUsuario.hashCode() : 0);
        return result;
    }
}
