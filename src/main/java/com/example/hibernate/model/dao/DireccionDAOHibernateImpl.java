package com.example.hibernate.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.hibernate.model.Direccion;

public class DireccionDAOHibernateImpl implements IDireccionDAO {

    private final SessionFactory sessionFactory;

    public DireccionDAOHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Direccion direccion) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(direccion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Direccion direccion) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(direccion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
    }

  

    @Override
    public void delete(Direccion direccion) {
        try (Session session = sessionFactory.openSession()) {
            // Primero buscamos si existe, **fuera de la transacción** (solo lectura)
            Direccion managed = session.get(Direccion.class, direccion.getId());
            if (managed == null) {
                throw new IllegalArgumentException("No existe ningún Direccion con id " + direccion.getId());
            }

            // Ahora abrimos la transacción para borrar
            Transaction tx = session.beginTransaction();
            session.remove(managed);
            tx.commit();
        }
    }

    @Override
    public Direccion findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Direccion.class, id);
        }
    }

    @Override
    public List<Direccion> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Direccion> query = session.createQuery("from Direccion", Direccion.class);
            return query.list();
        }
    }

    //Consultas con join

     public List<Direccion> direccionesPorComunidad(String nombreComunidad) {
         try (Session session = sessionFactory.openSession()) {
            
            //Probad a quitar los fetch y ver qué ocurre
            return session.createSelectionQuery(
                    "select d FROM Direccion d " +
                            "join fetch d.provincia p " +
                            "join fetch p.comunidadAutonoma ca " +
                            "where ca.nombre like :nombreComunidad",
                    Direccion.class).setParameter("nombreComunidad", nombreComunidad).getResultList();
        }
    }

    public List<Direccion> direccionesPorComunidadImplicit(String nombreComunidad) {
        try (Session session = sessionFactory.openSession()) {
            return session.createSelectionQuery(
                    "select d FROM Direccion d " +
                            "where d.provincia.comunidadAutonoma.nombre like :nombreComunidad",
                    Direccion.class).setParameter("nombreComunidad", nombreComunidad).getResultList();
        }
    }


   

}
