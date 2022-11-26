package biblioteca.java.javabiblioteca.managebeans;

import biblioteca.java.javabiblioteca.entities.EditorialesEntity;
import biblioteca.java.javabiblioteca.entities.LibrosEntity;
import biblioteca.java.javabiblioteca.models.EditorialModel;
import biblioteca.java.javabiblioteca.util.JsfUtil;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@ManagedBean
@RequestScoped
@Getter
@Setter
public class EditorialBeans {
    EditorialModel editorialModel = new EditorialModel();

    EditorialesEntity editorialesEntity;

    public EditorialBeans() {
        editorialesEntity = new EditorialesEntity();
    }

    public List<EditorialesEntity> listaEditorial() {
        return editorialModel.obtenerEditoriales();
    }

    public String insertarEditorial() {
        if (!editorialModel.insertarEditorial(editorialesEntity)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Esta editorial ya ha sido ingresada"));
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Editorial ingresada de forma exitosa"));
        }
        return null;//Regreso a la misma página
    }

    public String eliminarEditorial() {
        int idEditorial = Integer.parseInt(JsfUtil.getRequest().getParameter("idEditorial"));
        if (editorialModel.eliminarEditorial(idEditorial)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Editorial eliminada"));
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No se pudo eliminar esta Editorial: " + idEditorial));
        }
        return null;
    }

    public String buscarById(int id) {
        EditorialesEntity editorialesEntity1 = new EditorialesEntity();
        List<EditorialesEntity> lista = editorialModel.obtenerEditorialesById(id);
        lista.forEach(list -> {
            editorialesEntity1.setIdEditorial(list.getIdEditorial());
            editorialesEntity1.setNombreEditorial(list.getNombreEditorial());
            editorialesEntity1.setContacto(list.getContacto());
            editorialesEntity1.setTelefono(list.getTelefono());
            editorialesEntity1.setDireccion(list.getDireccion());
        });
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("editorial", editorialesEntity1);
        return "modificar_editoriales.xhmtl";
    }

    public String modificarEditorial(String nombreEditorial, String contacto, String telefono, String direccion, int idEditorial) {
        EditorialesEntity editorialesEntity1 = new EditorialesEntity();
        editorialesEntity1.setIdEditorial(idEditorial);
        editorialesEntity1.setNombreEditorial(nombreEditorial);
        editorialesEntity1.setContacto(contacto);
        editorialesEntity1.setTelefono(telefono);
        editorialesEntity1.setDireccion(direccion);
        if (!editorialModel.modificarEditorial(editorialesEntity1)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No se pudo modificar el libro."));
            return null;
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Libro modificado correctamente"));
            return "ver_editoriales.xhtml";
        }
    }
}
