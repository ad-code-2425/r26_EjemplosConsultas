package com.example.hibernate.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.SelectionQuery;

import com.example.hibernate.dto.ProfesorInfo;
import com.example.hibernate.model.Profesor;

public class ProfesorDAOHibernateImpl implements IProfesorDAO {

    private final SessionFactory sessionFactory;

    public ProfesorDAOHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Profesor profesor) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(profesor);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Profesor profesor) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(profesor);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
    }

    // @Override
    // public void delete(Profesor profesor) {
    // Transaction tx = null;
    // try (Session session = sessionFactory.openSession()) {
    // tx = session.beginTransaction();
    // session.remove(profesor);
    // tx.commit();
    // } catch (Exception e) {
    // if (tx != null)
    // tx.rollback();
    // e.printStackTrace();
    // }
    // }

    @Override
    public void delete(Profesor profesor) {
        try (Session session = sessionFactory.openSession()) {
            // Primero buscamos si existe, **fuera de la transacción** (solo lectura)
            Profesor managed = session.get(Profesor.class, profesor.getId());
            if (managed == null) {
                throw new IllegalArgumentException("No existe ningún Profesor con id " + profesor.getId());
            }

            // Ahora abrimos la transacción para borrar
            Transaction tx = session.beginTransaction();
            session.remove(managed);
            tx.commit();
        }
    }

    @Override
    public Profesor findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Profesor.class, id);
        }
    }

    @Override
    public List<Profesor> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Profesor> query = session.createQuery("from Profesor", Profesor.class);
            return query.list();
        }
    }

    // Métodos extra de consultas

    @Override
    public List<ProfesorInfo> findAllProfesorInfo() {
        try (Session session = sessionFactory.openSession()) {

            return ( List<ProfesorInfo>) session.createSelectionQuery(
                    "SELECT new com.example.hibernate.dto.ProfesorInfo(" +
                            "p.nombre || ' ' || p.ape1 || ' ' || p.ape2) " +
                            "FROM Profesor p",
                    ProfesorInfo.class)
                    .getResultList();
        }
    }

    @Override
    public List<Object[]> findIdAndNombre() {
        try (Session session = sessionFactory.openSession()) {
            List<Object[]> datos = session
                    .createSelectionQuery("SELECT p.id, p.nombre FROM Profesor p", Object[].class)
                    .getResultList();
            return datos;
        }
    }

    // Con parámetro nominal
    public List<Profesor> findProfesorByNombre(String nombre) {
        try (Session session = sessionFactory.openSession()) {
            SelectionQuery<Profesor> query = session.createSelectionQuery(
                    "FROM Profesor p WHERE p.nombre = :nombre", Profesor.class);
            query.setParameter("nombre", nombre);
            return query.getResultList();
        }
    }

    // Con parámetro posicional
    public List<Profesor> findProfesorByNombre2(String nombre) {
        try (Session session = sessionFactory.openSession()) {
            SelectionQuery<Profesor> query = session.createSelectionQuery(
                    "FROM Profesor p WHERE p.nombre = ?1", Profesor.class);
            query.setParameter(1, nombre);
            return query.getResultList();
        }
    }

    // Con parámetro posicional
    public List<Profesor> findProfesoresByPage(int pagina, int tamanhoPagina) {
        try (Session session = sessionFactory.openSession()) {

            return session
                    .createSelectionQuery("SELECT p FROM Profesor p ORDER BY p.id", Profesor.class)
                    .setMaxResults(tamanhoPagina)
                    .setFirstResult(pagina * tamanhoPagina)
                    .getResultList();

        }
    }

    public List<Profesor> findProfesorByNombreApe1Ape2(String nombre, String apellido1, String apellido2) {
        try (Session session = sessionFactory.openSession()) {
            Query<Profesor> query = session.createNamedQuery("Profesor_findByNombreApe1Ape2", Profesor.class);
            query.setParameter("nombre", nombre);
            query.setParameter("apellido1", apellido1);
            query.setParameter("apellido2", apellido2);
            return query.getResultList();
        }
    }

    public List<Object[]> findProfesoresCountByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createSelectionQuery(
                    "SELECT p.nombre, COUNT(p.nombre) " +
                            "FROM Profesor p " +
                            "GROUP BY p.nombre " +
                            "HAVING p.nombre LIKE :name",
                    Object[].class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }

    // Ejemplos de uso de join

    public List<Object[]> crossJoinProfesorModulo() {
        try (Session session = sessionFactory.openSession()) {
            return session.createSelectionQuery(
                    "select p.nombre, p.ape1, p.ape2, m.nombre " +
                            "FROM Profesor p, Modulo m",
                    Object[].class).getResultList();
        }

    }

    public List<Object[]> crossJoinConMemberOf() {
    try (Session session = sessionFactory.openSession()) {
        return session.createSelectionQuery(
            "select p.nombre, p.ape1, p.ape2, m.nombre " +
            "FROM Profesor p, Modulo m " +
            "WHERE m member of p.modulos",
            Object[].class)
                .getResultList();
    }
}

    public List<Object[]> innerJoinProfesorModulos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createSelectionQuery(
                    "select p.nombre, p.ape1, p.ape2, m.nombre " +
                            "FROM Profesor p join p.modulos m",
                    Object[].class).getResultList();
        }
    }

    public List<Object[]> joinProfesorYModulo() {
    try (Session session = sessionFactory.openSession()) {
        return session.createSelectionQuery(
            "select p, m FROM Profesor p join p.modulos m",
            Object[].class
        ).getResultList();
    }
}

 
    public List<Object[]> leftJoinProfesorModulos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createSelectionQuery(
                    "select p.nombre, p.ape1, p.ape2, m.nombre " +
                            "FROM Profesor p left join p.modulos m",
                    Object[].class).getResultList();
        }
    }

}
