package biblioteca.java.javabiblioteca.managebeans;


import biblioteca.java.javabiblioteca.entities.LibrosEntity;
import biblioteca.java.javabiblioteca.models.LibroModel;
import biblioteca.java.javabiblioteca.util.JsfUtil;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.ServletContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFile;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ManagedBean
@SessionScoped
@Getter
@Setter
public class LibrosBeans implements Serializable {
    private int idAutorForm;
    private int idEditorialForm;
    private int idGeneroForm;
    private UploadedFile file;

    private String imageTemp;
    LibroModel libroModel = new LibroModel();
    LibrosEntity librosEntity;

    public LibrosBeans() {
        librosEntity = new LibrosEntity();
    }

    public List<LibrosEntity> listaLibros() {
        return libroModel.obtenerLibros();
    }

    public String insertarLibro() throws IOException {
        if (file != null) {
            File fileImage = new File("F:\\TAREAS_2022\\DWF\\JavaBiblioteca\\src\\main\\webapp\\resources\\img\\" + file.getFileName());
            InputStream inputStream = file.getInputStream();
            saveImage(inputStream, fileImage);
            if (!libroModel.insertarLibro(librosEntity, idAutorForm, idEditorialForm, idGeneroForm, file)) {
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No se pudo agregar este libro."));
            } else {
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Libro agregado con exito"));
            }
        } else {
            FacesMessage message = new FacesMessage("Error", file.getFileName() + " no se puedo subir esta imagen.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    public String eliminarLibro() {
        int idLibro = Integer.parseInt(JsfUtil.getRequest().getParameter("idLibro"));
        if (libroModel.eliminarLibro(idLibro)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Libro eliminado"));
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No se pudo eliminar este libro: " + idLibro));
        }
        return null;
    }

    public String buscarById(int id) {
        LibrosEntity librosEntity1 = new LibrosEntity();
        List<LibrosEntity> lista = libroModel.obtenerLibroById(id);
        lista.forEach(list -> {
            librosEntity1.setIdLibro(list.getIdLibro());
            librosEntity1.setCodigoLibro(list.getCodigoLibro());
            librosEntity1.setTitulo(list.getTitulo());
            librosEntity1.setDescripcion(list.getDescripcion());
            librosEntity1.setAutoresByIdAutor(list.getAutoresByIdAutor());
            librosEntity1.setEditorialesByIdEditorial(list.getEditorialesByIdEditorial());
            librosEntity1.setGenerosByIdGenero(list.getGenerosByIdGenero());
            librosEntity1.setFechaEdicion(list.getFechaEdicion());
            librosEntity1.setIsbn(list.getIsbn());
            librosEntity1.setPaginas(list.getPaginas());
            librosEntity1.setEjemplares(list.getEjemplares());
        });
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("libro", librosEntity1);
        return "modificar_libro.xhmtl";
    }

    public String modificarLibros(String codigoLibro, String titulo, String descripcion, String isbn, int paginas, int ejemplares, int idLibro,
                                  Date fechaEdicion) {
        LibrosEntity librosEntity1 = new LibrosEntity();
        librosEntity1.setIdLibro(idLibro);
        librosEntity1.setCodigoLibro(codigoLibro);
        librosEntity1.setTitulo(titulo);
        librosEntity1.setDescripcion(descripcion);
        librosEntity1.setFechaEdicion(fechaEdicion);
        librosEntity1.setIsbn(isbn);
        librosEntity1.setPaginas(paginas);
        librosEntity1.setEjemplares(ejemplares);
        if (!libroModel.modificarLibro(librosEntity1, idAutorForm, idEditorialForm, idGeneroForm)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No se pudo modificar el libro."));
            return null;
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Libro modificado correctamente"));
            return "ver_libros.xhtml";
        }
    }

    public void saveImage(InputStream inputStream, File ImageFile) throws IOException {
        OutputStream outputStream = new FileOutputStream(ImageFile);
        IOUtils.copy(inputStream, outputStream);
    }

    public List<LibrosEntity> verHistorialLibro(int idLibro) {
        return libroModel.verHistorialLibro(idLibro);
    }

    public String verHistorial(int id) {
        LibrosEntity librosEntity1 = new LibrosEntity();
        List<LibrosEntity> lista = libroModel.obtenerLibroById(id);
        lista.forEach(list -> {
            librosEntity1.setIdLibro(list.getIdLibro());
            librosEntity1.setCodigoLibro(list.getCodigoLibro());
            librosEntity1.setTitulo(list.getTitulo());
        });
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("libro", librosEntity1);
        return "ver_historial_libro.xhmtl";
    }
}
