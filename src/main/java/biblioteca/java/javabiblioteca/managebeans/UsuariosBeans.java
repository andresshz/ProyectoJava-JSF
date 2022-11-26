package biblioteca.java.javabiblioteca.managebeans;

import biblioteca.java.javabiblioteca.entities.PrestamosEntity;
import biblioteca.java.javabiblioteca.entities.UsuariosEntity;
import biblioteca.java.javabiblioteca.models.UsuarioModel;
import biblioteca.java.javabiblioteca.util.JsfUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean
@SessionScoped
@Getter
@Setter
public class UsuariosBeans implements Serializable {

    private int idCargoForm;

    private UsuariosEntity Usuario;
    UsuarioModel usuarioModel = new UsuarioModel();
    UsuariosEntity usuariosEntity;

    public UsuariosBeans() {
        usuariosEntity = new UsuariosEntity();
    }

    public List<UsuariosEntity> listaUsuariosAdmin() {
        return usuarioModel.obtenerUsuariosAdmin();
    }

    public List<UsuariosEntity> listaUsuariosBibliotecarios() {
        return usuarioModel.obtenerUsuariosBibliotecarios();
    }

    public List<UsuariosEntity> listaUsuariosEstudiantes() {
        return usuarioModel.obtenerUsuariosEstudiantes();
    }

    public List<UsuariosEntity> listaUsuariosProfesores() {
        return usuarioModel.obtenerUsuariosProfesores();
    }

    public List<UsuariosEntity> listaUsuariosExternos() {
        return usuarioModel.obtenerUsuariosExternos();
    }

    public String insertarUsuario() {
        if (!usuarioModel.insertarUsuario(usuariosEntity, idCargoForm)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No se pudo agregar este usuario."));
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Usuario agregado con exito"));
        }
        return null;
    }

    public String eliminarUsuario() {
        int idUsuario = Integer.parseInt(JsfUtil.getRequest().getParameter("idUsuario"));
        if (usuarioModel.eliminarUsuario(idUsuario)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Usuario eliminado"));
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No se pudo eliminar este usuario: " + idUsuario));
        }
        return null;
    }

    public String buscarById(int id) {
        UsuariosEntity usuariosEntity1 = new UsuariosEntity();
        List<UsuariosEntity> lista = usuarioModel.obtenerUsuarioById(id);
        lista.forEach(list -> {
            usuariosEntity1.setIdUsuario(list.getIdUsuario());
            usuariosEntity1.setCarnet(list.getCarnet());
            usuariosEntity1.setNombres(list.getNombres());
            usuariosEntity1.setApellidos(list.getApellidos());
            usuariosEntity1.setTelefono(list.getTelefono());
            usuariosEntity1.setCorreo(list.getCorreo());
            usuariosEntity1.setDireccion(list.getDireccion());
            usuariosEntity1.setPassword(list.getPassword());
        });
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("usuario", usuariosEntity1);
        return "modificar_usuario.xhmtl";
    }

    public String modificarUsuarios(String carnet, String nombres, String apellidos, String telefono, String correo, String direccion, String password, int idUsuario) {
        UsuariosEntity usuariosEntity1 = new UsuariosEntity();
        usuariosEntity1.setIdUsuario(idUsuario);
        usuariosEntity1.setCarnet(carnet);
        usuariosEntity1.setNombres(nombres);
        usuariosEntity1.setApellidos(apellidos);
        usuariosEntity1.setTelefono(telefono);
        usuariosEntity1.setCorreo(correo);
        usuariosEntity1.setDireccion(direccion);
        usuariosEntity1.setPassword(password);
        if (!usuarioModel.modificarUsuario(usuariosEntity1, idCargoForm)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No se pudo modificar el usuario."));
            return null;
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Usuario modificado correctamente"));
            return "ver_bibliotecarios.xhtml";
        }
    }

    public String loginUsuario() {
        if (!usuarioModel.loginUsuario(usuariosEntity)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Contraseña y/o Carnet incorrectos!!!."));
            return null;
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "Logeado correctamente"));

            UsuariosEntity usuariosEntity2 = new UsuariosEntity();
            List<UsuariosEntity> lista = usuarioModel.obtenerUsuarioLogueado(usuariosEntity);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

            lista.forEach(list -> {
                usuariosEntity2.setIdUsuario(list.getIdUsuario());
                usuariosEntity2.setCarnet(list.getCarnet());
                usuariosEntity2.setNombres(list.getNombres());
                usuariosEntity2.setApellidos(list.getApellidos());
                usuariosEntity2.setCargoUsuariosEntity(list.getCargoUsuariosEntity());
            });
            session.setAttribute("id", usuariosEntity2.getIdUsuario());
            session.setAttribute("carnet", usuariosEntity2.getCarnet());
            session.setAttribute("nombres", usuariosEntity2.getNombres());
            session.setAttribute("apellidos", usuariosEntity2.getApellidos());
            session.setAttribute("idCargo", usuariosEntity2.getCargoUsuariosEntity().getIdCargoUsuario());
            session.setAttribute("nombreCargo", usuariosEntity2.getCargoUsuariosEntity().getCargoUsuario());
            return "home.xhmtl";
        }
    }

    public String registrarUsuario() {
        if (!usuarioModel.insertarUsuario(usuariosEntity, idCargoForm)) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No se pudo agregar este usuario, el carnet ya exite."));
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "La cuenta se ha creado correctamente."));
            return "LoginUsuario.xhtml";
        }
        return null;
    }

    public String cerrarSesion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("id", null);
        session.setAttribute("carnet", null);
        session.setAttribute("nombres", null);
        session.setAttribute("apellidos", null);
        session.setAttribute("idCargo", null);
        session.setAttribute("nombreCargo", null);
        FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "La sesión se ha cerrado correctamente."));
        return "LoginUsuario.xhtml";
    }

    public String usuarioNoLogueado() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

        String verificarNombre = (String) session.getAttribute("nombres");

        if (verificarNombre == null) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, "No existe una sesión activa, por favor ingresa tus credenciales."));
            return "LoginUsuario.xhtml";
        } else {
            return null;
        }
    }

    //Historial de acciones prestamos
    public String historialAcciones(int id) {
        UsuariosEntity usuariosEntity1 = new UsuariosEntity();
        List<UsuariosEntity> lista = usuarioModel.obtenerUsuarioById(id);
        lista.forEach(list -> {
            usuariosEntity1.setIdUsuario(list.getIdUsuario());
        });
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("usuario", usuariosEntity1);
        return "ver_historial_acciones.xhmtl";
    }

    public List<PrestamosEntity> verAccionesPrestadas(int idUsuario) {
        return usuarioModel.verAccionesPrestadas(idUsuario);
    }

    public List<PrestamosEntity> verAccionesCanceladas(int idUsuario) {
        return usuarioModel.verAccionesCanceladas(idUsuario);
    }

    public List<PrestamosEntity> verAccionesDevueltas(int idUsuario) {
        return usuarioModel.verAccionesDevueltas(idUsuario);
    }

    //Historial de prestamos

    public String historialPrestamos(int id) {
        UsuariosEntity usuariosEntity1 = new UsuariosEntity();
        List<UsuariosEntity> lista = usuarioModel.obtenerUsuarioById(id);
        lista.forEach(list -> {
            usuariosEntity1.setIdUsuario(list.getIdUsuario());
        });
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("usuario", usuariosEntity1);
        return "ver_historial_prestamos.xhmtl";
    }

    public List<PrestamosEntity> verHistorialPrestamos(int idUsuario) {
        return usuarioModel.verHistorialPrestamos(idUsuario);
    }
}
