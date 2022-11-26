package biblioteca.java.javabiblioteca.managebeans;

import biblioteca.java.javabiblioteca.entities.CargoUsuariosEntity;
import biblioteca.java.javabiblioteca.models.CargoUsuarioModel;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ManagedBean
@SessionScoped
@Getter
@Setter
public class CargoUsuariosBeans {
    CargoUsuarioModel cargoUsuarioModel = new CargoUsuarioModel();
    CargoUsuariosEntity cargoUsuariosEntity;

    public CargoUsuariosBeans() {
        cargoUsuariosEntity = new CargoUsuariosEntity();
    }

    public List<CargoUsuariosEntity> listaCargoUsuariosCrud() {
        return cargoUsuarioModel.obtenerCargoUsuariosCrud();
    }

    public List<CargoUsuariosEntity> listaCargoUsuariosRegistro() {
        return cargoUsuarioModel.obtenerCargoUsuariosRegistro();
    }
}
