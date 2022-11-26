package biblioteca.java.javabiblioteca.managebeans;


import biblioteca.java.javabiblioteca.entities.LibrosEntity;
import biblioteca.java.javabiblioteca.entities.PrestamosEntity;
import biblioteca.java.javabiblioteca.entities.UsuariosEntity;
import biblioteca.java.javabiblioteca.models.PrestamosModel;
import biblioteca.java.javabiblioteca.util.JsfUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ManagedBean
@RequestScoped
@Getter
@Setter
public class PrestamosBeans {
    PrestamosModel prestamosModel = new PrestamosModel();
    PrestamosEntity prestamosEntity;

    public PrestamosBeans() {
        prestamosEntity = new PrestamosEntity();
    }

    public List<PrestamosEntity> listaPrestamos() {
        return prestamosModel.obtenerPrestamos();
    }

    public List<PrestamosEntity> listaSolicitados() {
        return prestamosModel.obtenerSolicitados();
    }

    public List<PrestamosEntity> listaPrestamosUsuario(int id) {
        return prestamosModel.obtenerPrestamosUsuarios(id);
    }

    public List<PrestamosEntity> listaRetrasados(){ return prestamosModel.obtenerRetrasados(); }

    public List<PrestamosEntity> listaCancelados(){ return prestamosModel.obtenerCancelados(); }
    public List<PrestamosEntity> listaDevueltos(){ return prestamosModel.obtenerDevueltos(); }
    public List<PrestamosEntity> listaPrestados(){ return prestamosModel.obtenerPrestados(); }
    public String insertarPrestamo(int idUsuarioPrestatario, int FormLibro) {
        if (!prestamosModel.insertarPrestamo(prestamosEntity, idUsuarioPrestatario, FormLibro)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Error al prestar el libro."));
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Prestamo registrado correctamente."));
            return "ver_prestamos_usuarios";
        }
        return null;
    }

    public String editarPrestamo(int idPrestamo) {
        PrestamosEntity prestamosEntity1 = new PrestamosEntity();
        List<PrestamosEntity> lista = prestamosModel.obtenerPrestamoById(idPrestamo);
        lista.forEach(list -> {
            prestamosEntity1.setIdPrestamo(list.getIdPrestamo());
            prestamosEntity1.setFechaFin(list.getFechaFin());
            prestamosEntity1.setFechaInicio(list.getFechaInicio());
            prestamosEntity1.setFechaRegistro(list.getFechaRegistro());
            prestamosEntity1.setCodigoPrestamo(list.getCodigoPrestamo());
            prestamosEntity1.setIdPrestamo(list.getIdPrestamo());
            prestamosEntity1.setEstadosByIdEstadoPrestamo(list.getEstadosByIdEstadoPrestamo());
            prestamosEntity1.setUsuariosByIdUsuarioPrestatario(list.getUsuariosByIdUsuarioPrestatario());
            prestamosEntity1.setUsuariosByIdUsuarioPrestador(list.getUsuariosByIdUsuarioPrestador());
            prestamosEntity1.setLibrosByIdLibro(list.getLibrosByIdLibro());
        });
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("prestamo", prestamosEntity1);
        return "modificar_prestamo.xhmtl";
    }

    public String modificarPrestamo(PrestamosEntity prestamo) {
        PrestamosEntity prestamosEntity1 = new PrestamosEntity();
        prestamosEntity1.setIdPrestamo(prestamo.getIdPrestamo());
        prestamosEntity1.setCodigoPrestamo(prestamo.getCodigoPrestamo());
        prestamosEntity1.setFechaInicio(prestamo.getFechaInicio());
        prestamosEntity1.setFechaFin(prestamo.getFechaFin());
        prestamosEntity1.setEstadosByIdEstadoPrestamo(prestamo.getEstadosByIdEstadoPrestamo());
        prestamosEntity1.setFechaRegistro(prestamo.getFechaRegistro());
        prestamosEntity1.setLibrosByIdLibro(prestamo.getLibrosByIdLibro());
        prestamosEntity1.setUsuariosByIdUsuarioPrestador(prestamo.getUsuariosByIdUsuarioPrestador());
        prestamosEntity1.setUsuariosByIdUsuarioPrestatario(prestamo.getUsuariosByIdUsuarioPrestatario());

        if (!prestamosModel.modificarPrestamo(prestamosEntity1)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Error. No se pudo modificar el prestamo"));
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", "Prestamo modificado exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return "ver_prestamos_usuarios";
    }

    public void eliminarPrestamo(int id) {
        if (!prestamosModel.eliminarPrestamo(id)) {
            JsfUtil.setErrorMessage(null, "Error: no se pudo eliminar el prestamo");
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "Prestamo eliminado exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public String aceptarPrestamo(PrestamosEntity prestamosEntity, int idUsuarioBibliotecario) {
        if (prestamosModel.actualizarPrestado(prestamosEntity, idUsuarioBibliotecario)) {
            if (prestamosModel.aceptarPrestamo(prestamosEntity, idUsuarioBibliotecario)) {
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Prestamo aceptado con exito."));
                return "Aprobar_denegar";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Error."));
        }
        return null;
    }

    public String cancelarPrestamo(PrestamosEntity prestamosEntity, int idUsuarioBibliotecario) {
        if (prestamosModel.actualizarPrestado(prestamosEntity, idUsuarioBibliotecario)) {
            if (prestamosModel.cancelarPrestamo(prestamosEntity, idUsuarioBibliotecario)) {
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "El prestamo ha sido cancelado."));
                return "Aprobar_denegar";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Error."));
        }
        return null;
    }
    //PDF
    public void crearPDF(int idUsuario) {
        List<PrestamosEntity> lista = prestamosModel.obtenerPrestamosByIdUsuario(idUsuario);

        String path = "C:/Users/rafae/Downloads/Reporte.pdf";

        try {
            PdfWriter pdfWriter = new PdfWriter(path);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);


            document.add(new Paragraph("Reporte de prestamo").setFontSize(22));
            //document.add(new Paragraph(new Text("\n")));

            Table table = new Table(6);

            table.addCell(new Cell().add(new Paragraph("Código prestamo"))).addCell(new Cell().add(new Paragraph("Usuario prestador"))).addCell(new Cell().add(new Paragraph("Estado"))).addCell(new Cell().add(new Paragraph("Fecha inicio"))).addCell(new Cell().add(new Paragraph("Fecha final"))).addCell(new Cell().add(new Paragraph("Fecha Registro")));

            lista.forEach(list -> {
                table.addCell(new Cell().add(new Paragraph(list.getCodigoPrestamo()))).addCell(new Cell().add(new Paragraph(list.getUsuariosByIdUsuarioPrestador().getNombres() + list.getUsuariosByIdUsuarioPrestador().getApellidos()))).addCell(new Cell().add(new Paragraph(list.getEstadosByIdEstadoPrestamo().getEstadoPrestamo()))).addCell(new Cell().add(new Paragraph(String.valueOf(list.getFechaInicio())))).addCell(new Cell().add(new Paragraph(String.valueOf(list.getFechaFin())))).addCell(new Cell().add(new Paragraph(String.valueOf(list.getFechaRegistro()))));
            });

            document.add(table);
            document.close();
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "PDF creado correctamente. Revisa tu carpeta de descargas."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "ERROR. El PDF no se pudo generar."));

        }
    }
}