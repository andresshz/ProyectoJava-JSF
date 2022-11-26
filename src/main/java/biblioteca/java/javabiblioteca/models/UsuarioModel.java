package biblioteca.java.javabiblioteca.models;

import biblioteca.java.javabiblioteca.entities.CargoUsuariosEntity;
import biblioteca.java.javabiblioteca.entities.PrestamosEntity;
import biblioteca.java.javabiblioteca.entities.UsuariosEntity;
import biblioteca.java.javabiblioteca.util.JpaUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UsuarioModel {
    public List<UsuariosEntity> obtenerUsuariosAdmin() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("UsuariosEntity.obtenerAdmin");
            List<UsuariosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<UsuariosEntity> obtenerUsuariosBibliotecarios() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("UsuariosEntity.obtenerBibliotecarios");
            List<UsuariosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<UsuariosEntity> obtenerUsuariosEstudiantes() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("UsuariosEntity.obtenerEstudiantes");
            List<UsuariosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<UsuariosEntity> obtenerUsuariosProfesores() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("UsuariosEntity.obtenerProfesores");
            List<UsuariosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<UsuariosEntity> obtenerUsuariosExternos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("UsuariosEntity.obtenerExternos");
            List<UsuariosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public boolean insertarUsuario(UsuariosEntity usuariosEntity, int idCargoForm) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();

        String carnet;
        Date date = new Date();
        SimpleDateFormat year = new SimpleDateFormat("yy");
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        SimpleDateFormat segundos = new SimpleDateFormat("ss");
        carnet = String.valueOf(usuariosEntity.getNombres().charAt(0)) + String.valueOf(usuariosEntity.getApellidos().charAt(0)) +
                year.format(date) + dia.format(date) + segundos.format(date);
        usuariosEntity.setCarnet(carnet.toUpperCase());

        try {
            /*
            Query consulta = em.createNamedQuery("UsuariosEntity.verificarCarnet").setParameter("carnet", usuariosEntity.getCarnet());
            List<UsuariosEntity> lista = consulta.getResultList();
            if (!lista.isEmpty()) {
                return false;
            }
             */
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
            String hash = argon2.hash(1, 1024, 1, usuariosEntity.getPassword());
            usuariosEntity.setPassword(hash);

            CargoUsuariosEntity cargoFormulario = em.find(CargoUsuariosEntity.class,
                    idCargoForm);
            usuariosEntity.setCargoUsuariosEntity(cargoFormulario);
            tran.begin();
            em.persist(usuariosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean eliminarUsuario(int idUsuario) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            UsuariosEntity est = em.find(UsuariosEntity.class, idUsuario);
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

    public List<UsuariosEntity> obtenerUsuarioById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("UsuariosEntity.obtenerById").setParameter("idUsuario", id);
            List<UsuariosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<UsuariosEntity> obtenerUsuarioLogueado(UsuariosEntity usuariosEntity) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("UsuariosEntity.obtenerUsuarioLogueado").setParameter("carnet", usuariosEntity.getCarnet());
            List<UsuariosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public boolean modificarUsuario(UsuariosEntity usuariosEntity, int idCargoForm) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
        String hash = argon2.hash(1, 1024, 1, usuariosEntity.getPassword());
        usuariosEntity.setPassword(hash);

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            CargoUsuariosEntity cargoFormulario = em.find(CargoUsuariosEntity.class,
                    idCargoForm);
            usuariosEntity.setCargoUsuariosEntity(cargoFormulario);
            tran.begin();
            em.merge(usuariosEntity);
            tran.commit();
            em.close();
            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean loginUsuario(UsuariosEntity entity) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("UsuariosEntity.verificarCarnet").setParameter("carnet", entity.getCarnet());
            List<UsuariosEntity> lista = consulta.getResultList();
            if (lista.isEmpty()) {
                return false;
            }
            String passwordBdd = lista.get(0).getPassword();
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
            if (argon2.verify(passwordBdd, entity.getPassword())) {
                em.close();
                return true;
            }
            return false;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public List<PrestamosEntity> verAccionesPrestadas(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.obtenerAccionesPrestadas").setParameter("idUsuario", id);
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<PrestamosEntity> verAccionesCanceladas(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.obtenerAccionesCanceladas").setParameter("idUsuario", id);
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<PrestamosEntity> verAccionesDevueltas(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("PrestamosEntity.obtenerAccionesDevueltas").setParameter("idUsuario", id);
            List<PrestamosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<PrestamosEntity> verHistorialPrestamos(int id) {
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
}
