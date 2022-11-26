package biblioteca.java.javabiblioteca.models;

import biblioteca.java.javabiblioteca.entities.GenerosEntity;
import biblioteca.java.javabiblioteca.util.JpaUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;

public class GeneroModels {

    public List<GenerosEntity> obtenerGeneros() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("GenerosEntity.obtener");
            List<GenerosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<GenerosEntity> obtenerGeneroById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("GenerosEntity.obtenerById").setParameter("idGenero", id);
            List<GenerosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public boolean InsertarGenero(GenerosEntity generosEntity) {

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.persist(generosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean ModificarGenero(GenerosEntity generosEntity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.merge(generosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean eliminarGenero(int id) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            GenerosEntity est = em.find(GenerosEntity.class, id);
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
}
