package biblioteca.java.javabiblioteca.models;

import biblioteca.java.javabiblioteca.entities.CargoUsuariosEntity;
import biblioteca.java.javabiblioteca.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class CargoUsuarioModel {
    public List<CargoUsuariosEntity> obtenerCargoUsuariosCrud() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("CargoUsuariosEntity.findAllCrud");
            List<CargoUsuariosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<CargoUsuariosEntity> obtenerCargoUsuariosRegistro() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("CargoUsuariosEntity.findAllRegistro");
            List<CargoUsuariosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }
}
