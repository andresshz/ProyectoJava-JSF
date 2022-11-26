package biblioteca.java.javabiblioteca.models;

import biblioteca.java.javabiblioteca.entities.*;
import biblioteca.java.javabiblioteca.util.JpaUtil;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.sql.Timestamp;
import java.util.List;

public class PrestamosModel {
    public List<PrestamosEntity> obtenerPrestamosByIdUsuario(int idUsuario) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.obtenerPrestamosByIdUsuario").setParameter("idUsuario", idUsuario);
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<PrestamosEntity> obtenerPrestamos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.findAll");
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<PrestamosEntity> obtenerPrestamosUsuarios(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.obtenerHistorialPrestamos").setParameter("idUsuario", id);
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public boolean insertarPrestamo(PrestamosEntity prestamosEntity, int idUsuarioPrestatario, int idLibro) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();

        String codigo = "";
        int cantidadPrestamos = 0;

        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.ultimoPrestamo");
            List<PrestamosEntity> listaPrestamos = consulta.getResultList();
            cantidadPrestamos = listaPrestamos.get(0).getIdPrestamo() + 1;

            if (cantidadPrestamos <= 9) {
                codigo = "000" + cantidadPrestamos;
            }

            if (cantidadPrestamos >= 10 && cantidadPrestamos <= 99) {
                codigo = "00" + cantidadPrestamos;
            }

            if (cantidadPrestamos >= 100 && cantidadPrestamos <= 999) {
                codigo = "0" + cantidadPrestamos;
            }

            if (cantidadPrestamos >= 1000 && cantidadPrestamos <= 9999) {
                codigo = String.valueOf(cantidadPrestamos);
            }

            prestamosEntity.setCodigoPrestamo("PRES" + codigo);

            UsuariosEntity usuariosPrestatario = em.find(UsuariosEntity.class, idUsuarioPrestatario);
            EstadoPrestamoEntity estadoPrestamo = em.find(EstadoPrestamoEntity.class, 1);
            LibrosEntity libro = em.find(LibrosEntity.class, idLibro);

            prestamosEntity.setUsuariosByIdUsuarioPrestatario(usuariosPrestatario);
            prestamosEntity.setEstadosByIdEstadoPrestamo(estadoPrestamo);
            prestamosEntity.setLibrosByIdLibro(libro);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            prestamosEntity.setFechaRegistro(timestamp);

            tran.begin();
            em.persist(prestamosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean modificarPrestamo(PrestamosEntity prestamosEntity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.merge(prestamosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean eliminarPrestamo(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            PrestamosEntity prestamo = em.find(PrestamosEntity.class, id);
            if (prestamo != null) {
                EntityTransaction tran = em.getTransaction();
                tran.begin();
                em.remove(prestamo);
                tran.commit();
            }
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public List<PrestamosEntity> obtenerPrestamoById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.obtenerPrestamoById").setParameter("idPrestamo", id);
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public Boolean aceptarPrestamo(PrestamosEntity prestamosEntity, int idUsuarioBibliotecario) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();

        try {
            EstadoPrestamoEntity estadoPrestamoEntity = new EstadoPrestamoEntity();
            estadoPrestamoEntity.setIdEstadoPrestamo(2);
            prestamosEntity.setEstadosByIdEstadoPrestamo(estadoPrestamoEntity);

            tran.begin();
            em.persist(prestamosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, e.getMessage()));
            return null;
        }
    }

    public Boolean cancelarPrestamo(PrestamosEntity prestamosEntity, int idUsuarioBibliotecario) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();

        try {
            EstadoPrestamoEntity estadoPrestamoEntity = new EstadoPrestamoEntity();
            estadoPrestamoEntity.setIdEstadoPrestamo(3);
            prestamosEntity.setEstadosByIdEstadoPrestamo(estadoPrestamoEntity);

            tran.begin();
            em.persist(prestamosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, e.getMessage()));
            return null;
        }
    }

    public Boolean actualizarPrestado(PrestamosEntity prestamosEntity, int idUsuarioBibliotecario) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();

        try {
            UsuariosEntity usuariosBibliotecario = em.find(UsuariosEntity.class,
                    idUsuarioBibliotecario);
            prestamosEntity.setUsuariosByIdUsuarioPrestador(usuariosBibliotecario);
            tran.begin();
            em.merge(prestamosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(null, e.getMessage()));
            return null;
        }
    }

    public List<PrestamosEntity> obtenerRetrasados(){
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.findByRetrasado");
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        }catch (Exception e){
            em.close();
            return null;
        }
    }
    public List<PrestamosEntity> obtenerCancelados(){
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.findByCancelados");
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        }catch (Exception e){
            em.close();
            return null;
        }
    }

    public List<PrestamosEntity> obtenerDevueltos(){
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.findByDevueltos");
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        }catch (Exception e){
            em.close();
            return null;
        }
    }
    public List<PrestamosEntity> obtenerPrestados(){
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.findByPrestados");
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        }catch (Exception e){
            em.close();
            return null;
        }
    }
    public List<PrestamosEntity> obtenerSolicitados() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.findBySolicitado");
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }
}