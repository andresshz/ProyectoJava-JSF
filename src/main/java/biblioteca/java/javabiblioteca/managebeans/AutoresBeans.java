package biblioteca.java.javabiblioteca.managebeans;

import biblioteca.java.javabiblioteca.entities.AutoresEntity;
import biblioteca.java.javabiblioteca.models.AutorModel;
import biblioteca.java.javabiblioteca.util.JsfUtil;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ManagedBean
@RequestScoped
@Getter
@Setter
public class AutoresBeans {
    AutorModel autorModel = new AutorModel();

    AutoresEntity autoresEntity;

    public AutoresBeans() {
        autoresEntity = new AutoresEntity();
    }

    public List<AutoresEntity> listaAutores() {
        return autorModel.obtenerAutores();
    }

    public String insertarAutor() {
        if (!autorModel.insertarAutor(autoresEntity)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "El autor ya se encuentra registrado."));
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Autor registrado correctamente."));
        }
        return null;
    }

    public String editarAutor(AutoresEntity autorEditar){
        autoresEntity = autorEditar;
        return "modificar_autor";
    }

    public String modificarAutor(){
        if(!autorModel.modificarAutor(autoresEntity)){
            JsfUtil.setErrorMessage(null, "Error: no se pudo modificar el autor");
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", "Autor modificado exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return "ver_autores";
    }

    public void eliminarAutor(int id){
        if(!autorModel.eliminarAutor(id)){
            JsfUtil.setErrorMessage(null, "Error: no se pudo eliminar el autor");
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "Autor eliminado exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}