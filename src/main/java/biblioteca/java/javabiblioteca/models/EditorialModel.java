package biblioteca.java.javabiblioteca.models;

import biblioteca.java.javabiblioteca.entities.EditorialesEntity;
import biblioteca.java.javabiblioteca.entities.LibrosEntity;
import biblioteca.java.javabiblioteca.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;

public class EditorialModel {
    public List<EditorialesEntity> obtenerEditoriales() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("EditorialesEntity.findAll");
            List<EditorialesEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<EditorialesEntity> obtenerEditorialesById(int idEditorial) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("EditorialesEntity.findByIdEditorial");
            consulta.setParameter("idEditorial", idEditorial);
            List<EditorialesEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public boolean insertarEditorial(EditorialesEntity editorialesEntity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.persist(editorialesEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean eliminarEditorial(int idEditorial) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            EditorialesEntity est = em.find(EditorialesEntity.class, idEditorial);
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

    public boolean modificarEditorial(EditorialesEntity editorialesEntity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.merge(editorialesEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }
}
