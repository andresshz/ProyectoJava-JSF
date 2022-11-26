package biblioteca.java.javabiblioteca.models;

import biblioteca.java.javabiblioteca.entities.AutoresEntity;
import biblioteca.java.javabiblioteca.entities.EditorialesEntity;
import biblioteca.java.javabiblioteca.entities.GenerosEntity;
import biblioteca.java.javabiblioteca.entities.LibrosEntity;
import biblioteca.java.javabiblioteca.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.primefaces.model.file.UploadedFile;

import java.util.List;

public class LibroModel {
    public List<LibrosEntity> obtenerLibros() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("LibrosEntity.findAll");
            List<LibrosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public boolean insertarLibro(LibrosEntity librosEntity, int idAutorForm, int idEditorialForm, int idGeneroForm, UploadedFile file) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();

        String codigo = "";
        int cantidadLibros = 0;
        librosEntity.setCodigoLibro(codigo);

        try {
            String tituloImagen = file.getFileName();
            AutoresEntity autorFormulario = em.find(AutoresEntity.class,
                    idAutorForm);
            EditorialesEntity editorialFormulario = em.find(EditorialesEntity.class,
                    idEditorialForm);
            GenerosEntity generoFormulario = em.find(GenerosEntity.class,
                    idGeneroForm);
            librosEntity.setAutoresByIdAutor(autorFormulario);
            librosEntity.setEditorialesByIdEditorial(editorialFormulario);
            librosEntity.setGenerosByIdGenero(generoFormulario);
            librosEntity.setImagenLibro(tituloImagen);
            tran.begin();
            em.persist(librosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean eliminarLibro(int idLibro) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            LibrosEntity est = em.find(LibrosEntity.class, idLibro);
            if (est != null) {
                EntityTransaction tran = em.getTransaction();
                tran.begin();
                em.remove(est);
                tran.commit();
            }
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public List<LibrosEntity> obtenerLibroById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("LibrosEntity.obtenerById").setParameter("idLibro", id);
            List<LibrosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public boolean modificarLibro(LibrosEntity librosEntity, int idAutorForm, int idEditorialForm, int idGeneroForm) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            AutoresEntity autorFormulario = em.find(AutoresEntity.class,
                    idAutorForm);
            EditorialesEntity editorialFormulario = em.find(EditorialesEntity.class,
                    idEditorialForm);
            GenerosEntity generoFormulario = em.find(GenerosEntity.class,
                    idGeneroForm);
            librosEntity.setAutoresByIdAutor(autorFormulario);
            librosEntity.setEditorialesByIdEditorial(editorialFormulario);
            librosEntity.setGenerosByIdGenero(generoFormulario);

            tran.begin();
            em.merge(librosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public List<LibrosEntity> verHistorialLibro(int idLibro) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("LibrosEntity.findHistorial").setParameter("idLibro", idLibro);
            List<LibrosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }
}
