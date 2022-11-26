package biblioteca.java.javabiblioteca.managebeans;

import biblioteca.java.javabiblioteca.entities.GenerosEntity;
import biblioteca.java.javabiblioteca.models.GeneroModels;
import biblioteca.java.javabiblioteca.util.JsfUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.CellEditEvent;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "generosBeans")
@RequestScoped
@Getter
@Setter
public class GenerosBeans implements Serializable {

    GeneroModels generoModels = new GeneroModels();
    GenerosEntity generosEntity;

    public GenerosBeans() {
        generosEntity = new GenerosEntity();
    }

    public List<GenerosEntity> listaGeneros() {
        return generoModels.obtenerGeneros();
    }

    public String InsertarGenero() {
        if (!generoModels.InsertarGenero(generosEntity)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "El género ya se encuentra registrado."));
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Genero insertado correctamente."));
        }
        return null;
    }

    public String ModificarGeneros(int id, String name) {
        GenerosEntity entity = new GenerosEntity();
        entity.setIdGenero(id);
        entity.setNombreGenero(name);
        if (!generoModels.ModificarGenero(entity)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Error al modificar."));
            return null;
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Genero Modificado correctamente."));
            return "GenerosData.xhtml";
        }

    }


    public String buscarById(int id) {
        GenerosEntity entity = new GenerosEntity();
        List<GenerosEntity> lista = generoModels.obtenerGeneroById(id);
        lista.forEach(list -> {
            entity.setIdGenero(list.getIdGenero());
            entity.setNombreGenero(list.getNombreGenero());
        });
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("genero", entity);
        return "GenerosMerge.xhmtl";
    }

    public String eliminarGenero() {
        int id = Integer.parseInt(JsfUtil.getRequest().getParameter("idGenero"));
        if (generoModels.eliminarGenero(id)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Genero eliminado exitosamente."));
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No se pudo borrar este genero. " + id));
        }
        return null;
    }

    public String redModificar() {
        int idGenero = Integer.parseInt(JsfUtil.getRequest().getParameter("idGenero"));

        return "GenerosMerge.xhmtl?faces-redirect=true&id=" + idGenero;
    }
}
