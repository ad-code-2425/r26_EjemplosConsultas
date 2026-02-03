package com.example.hibernate.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;

import com.example.hibernate.model.Ciclo;

public class CicloDAOHibernateImpl implements ICicloDAO {

    private final SessionFactory sessionFactory;

    public CicloDAOHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Ciclo ciclo) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(ciclo); // Se puede usar save() también
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Ciclo ciclo) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(ciclo); // merge para actualizar la entidad
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
    }

    // @Override
    // public void delete(Ciclo ciclo) {
    // Transaction tx = null;
    // try (Session session = sessionFactory.openSession()) {
    // tx = session.beginTransaction();
    // session.remove(ciclo);
    // tx.commit();
    // } catch (Exception e) {
    // if (tx != null)
    // tx.rollback();
    // e.printStackTrace();
    // }
    // }

    @Override
    public void delete(Ciclo ciclo) {
        try (Session session = sessionFactory.openSession()) {
            // Primero buscamos si existe, **fuera de la transacción** (solo lectura)
            Ciclo managed = session.get(Ciclo.class, ciclo.getIdCiclo());
            if (managed == null) {
                throw new IllegalArgumentException("No existe ningún Ciclo con id " + ciclo.getIdCiclo());
            }

            // Ahora abrimos la transacción para borrar
            Transaction tx = session.beginTransaction();
            session.remove(managed);
            tx.commit();
        }
    }

    @Override
    public Ciclo findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            // find mejor que get porque es JPA estándar
            return session.find(Ciclo.class, id);
        }
    }

    @Override
    public List<Ciclo> findAll() {
        try (Session session = sessionFactory.openSession()) {
            SelectionQuery<Ciclo> query = session.createSelectionQuery("from Ciclo c ORDER BY c.nombre", Ciclo.class);
            return query.list();
        }
    }
    // Consultas extra de ciclo

    public List<Object[]> findCiclosStats()
    {
        try (Session session = sessionFactory.openSession()) {
            return session.createSelectionQuery(
                    "SELECT AVG(c.horas), SUM(c.horas), MIN(c.horas), MAX(c.horas), COUNT(*) " +
                            "FROM Ciclo c",
                    Object[].class)
                    .getResultList();
        }
    }

}
