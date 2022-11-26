package biblioteca.java.javabiblioteca.models;

import biblioteca.java.javabiblioteca.entities.AutoresEntity;
import biblioteca.java.javabiblioteca.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;

public class AutorModel {
    public List<AutoresEntity> obtenerAutores() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("AutoresEntity.findAll");
            List<AutoresEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public boolean insertarAutor(AutoresEntity autor) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.persist(autor);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean modificarAutor(AutoresEntity autor) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.merge(autor);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean eliminarAutor(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            AutoresEntity autor = em.find(AutoresEntity.class, id);
            if (autor != null) {
                EntityTransaction tran = em.getTransaction();
                tran.begin();
                em.remove(autor);
                tran.commit();
            }
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }
}