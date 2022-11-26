package biblioteca.java.javabiblioteca.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios", schema = "biblioteca_dwf")
@NamedQueries({
        @NamedQuery(name = "UsuariosEntity.obtenerAdmin", query = "SELECT u FROM UsuariosEntity u WHERE u.cargoUsuariosEntity.idCargoUsuario = 1 ORDER BY u.cargoUsuariosEntity.cargoUsuario"),
        @NamedQuery(name = "UsuariosEntity.obtenerBibliotecarios", query = "SELECT u FROM UsuariosEntity u WHERE u.cargoUsuariosEntity.idCargoUsuario = 2 ORDER BY u.cargoUsuariosEntity.cargoUsuario"),
        @NamedQuery(name = "UsuariosEntity.obtenerEstudiantes", query = "SELECT u FROM UsuariosEntity u WHERE u.cargoUsuariosEntity.idCargoUsuario = 3 ORDER BY u.cargoUsuariosEntity.cargoUsuario"),
        @NamedQuery(name = "UsuariosEntity.obtenerProfesores", query = "SELECT u FROM UsuariosEntity u WHERE u.cargoUsuariosEntity.idCargoUsuario = 4 ORDER BY u.cargoUsuariosEntity.cargoUsuario"),
        @NamedQuery(name = "UsuariosEntity.obtenerExternos", query = "SELECT u FROM UsuariosEntity u WHERE u.cargoUsuariosEntity.idCargoUsuario = 5 ORDER BY u.cargoUsuariosEntity.cargoUsuario"),
        @NamedQuery(name = "UsuariosEntity.obtenerById", query = "SELECT u FROM UsuariosEntity u WHERE u.idUsuario = :idUsuario"),
        @NamedQuery(name = "UsuariosEntity.obtenerUsuarioLogueado", query = "SELECT u FROM UsuariosEntity u WHERE u.carnet = :carnet"),
        @NamedQuery(name = "UsuariosEntity.verificarCarnet", query = "SELECT u FROM UsuariosEntity u WHERE u.carnet = :carnet")
})

@Getter
@Setter
public class UsuariosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;
    @Basic
    @Column(name = "carnet", nullable = false, length = 8)
    private String carnet;
    @Basic
    @Column(name = "nombres", nullable = false, length = 25)
    private String nombres;
    @Basic
    @Column(name = "apellidos", nullable = false, length = 25)
    private String apellidos;
    @Basic
    @Column(name = "telefono", nullable = false, length = 8)
    private String telefono;
    @Basic
    @Column(name = "correo", nullable = false, length = 30)
    private String correo;
    @Basic
    @Column(name = "direccion", nullable = false, length = 100)
    private String direccion;
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Basic
    @Column(name = "token", nullable = true, length = 10)
    private String token;
    @Basic
    @Column(name = "verificado", nullable = false)
    private int verificado;

    /*
     **
     * RELACIONES
     *
     */

    //Tabla cargos usuarios
    @ManyToOne
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo_usuario", nullable = false)
    private CargoUsuariosEntity cargoUsuariosEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsuariosEntity that = (UsuariosEntity) o;

        if (idUsuario != that.idUsuario) return false;
        //if (idCargo != that.idCargo) return false;
        if (verificado != that.verificado) return false;
        if (carnet != null ? !carnet.equals(that.carnet) : that.carnet != null) return false;
        if (nombres != null ? !nombres.equals(that.nombres) : that.nombres != null) return false;
        if (apellidos != null ? !apellidos.equals(that.apellidos) : that.apellidos != null) return false;
        if (telefono != null ? !telefono.equals(that.telefono) : that.telefono != null) return false;
        if (correo != null ? !correo.equals(that.correo) : that.correo != null) return false;
        if (direccion != null ? !direccion.equals(that.direccion) : that.direccion != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario;
        //result = 31 * result + idCargo;
        result = 31 * result + (carnet != null ? carnet.hashCode() : 0);
        result = 31 * result + (nombres != null ? nombres.hashCode() : 0);
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (correo != null ? correo.hashCode() : 0);
        result = 31 * result + (direccion != null ? direccion.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + verificado;
        return result;
    }
}
