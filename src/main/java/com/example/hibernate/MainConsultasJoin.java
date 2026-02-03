package com.example.hibernate;

import java.util.List;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import com.example.hibernate.model.Direccion;
import com.example.hibernate.model.Modulo;
import com.example.hibernate.model.Profesor;
import com.example.hibernate.model.dao.DireccionDAOHibernateImpl;
import com.example.hibernate.model.dao.IDireccionDAO;
import com.example.hibernate.model.dao.IProfesorDAO;
import com.example.hibernate.model.dao.ProfesorDAOHibernateImpl;
import com.example.hibernate.util.HibernateUtil;

public class MainConsultasJoin {

    private static final Scanner scanner = new Scanner(System.in);
    private static IProfesorDAO profesorDAO;
    private static IDireccionDAO direccionDAO;

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        profesorDAO = new ProfesorDAOHibernateImpl(sessionFactory);
        direccionDAO = new DireccionDAOHibernateImpl(sessionFactory);

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1:
                    ejecutarQ1();
                    break;
                case 2:
                    ejecutarQ2();
                    break;
                case 3:
                    ejecutarQ3();
                    break;
                case 4:
                    ejecutarQ4();
                    break;
                case 5:
                    ejecutarQ5();
                    break;
                case 6:
                    ejecutarQ6();
                    break;
                case 7:
                    ejecutarQ7();
                    break;
                case 0:
                    salir = true;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
        sessionFactory.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n==============================");
        System.out.println(" Consultas HQL - Q1..Q7");
        System.out.println("==============================");
        System.out.println("1. Q1. Cross join (no recomendado)");
        System.out.println("2. Q2. Cross join filtrado con member of");
        System.out.println("3. Q3. Inner join (recomendado)");
        System.out.println("4. Q4. Join devolviendo entidades completas");
        System.out.println("5. Q5. Left join");
        System.out.println("6. Q6. Join a través de asociaciones (direcciones - Galicia)");
        System.out.println("7. Q7. Navegación por propiedades (direcciones - Galicia)");
        System.out.println("0. Salir");
        System.out.println("==============================");
    }

    private static void ejecutarQ1() {
        System.out.println("\n--- Q1. Cross join (no recomendado) ---");
        List<Object[]> rows =  profesorDAO.crossJoinProfesorModulo();
        if (rows.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }
        int idx = 1;
        for (Object[] r : rows) {
            System.out.println(" " + idx + ". " + (r[0] != null ? r[0] : "null") + " " + (r[1] != null ? r[1] : "") + " "
                    + (r[2] != null ? r[2] : "") + " -> Modulo: " + (r[3] != null ? r[3] : "null"));
            idx++;
        }
    }

    private static void ejecutarQ2() {
        System.out.println("\n--- Q2. Cross join filtrado con member of ---");
        List<Object[]> rows =  profesorDAO.crossJoinConMemberOf();
        if (rows.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }
        int idx = 1;
        for (Object[] r : rows) {
            System.out.println(" " + idx + ". " + (r[0] != null ? r[0] : "null") + " " + (r[1] != null ? r[1] : "") + " "
                    + (r[2] != null ? r[2] : "") + " -> Modulo: " + (r[3] != null ? r[3] : "null"));
            idx++;
        }
    }

    private static void ejecutarQ3() {
        System.out.println("\n--- Q3. Inner join (recomendado) ---");
        List<Object[]> rows =  profesorDAO.innerJoinProfesorModulos();
        if (rows.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }
        int idx = 1;
        for (Object[] r : rows) {
            System.out.println(" " + idx + ". " + (r[0] != null ? r[0] : "null") + " " + (r[1] != null ? r[1] : "") + " "
                    + (r[2] != null ? r[2] : "") + " -> Modulo: " + (r[3] != null ? r[3] : "null"));
            idx++;
        }
    }

    private static void ejecutarQ4() {
        System.out.println("\n--- Q4. Join devolviendo entidades completas ---");
        List<Object[]> rows =  profesorDAO.joinProfesorYModulo();
        if (rows.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }
        int idx = 1;
        for (Object[] r : rows) {
            Profesor p = (r[0] instanceof Profesor) ? (Profesor) r[0] : null;
            Modulo m = (r[1] instanceof Modulo) ? (Modulo) r[1] : null;
            String profesorStr = (p != null) ? (p.getNombre() + " " + p.getApe1() + " " + p.getApe2()) : "null";
            String moduloStr = (m != null) ? m.getNombre() : "null";
            System.out.println(" " + idx + ". " + profesorStr + " -> Modulo: " + moduloStr);
            idx++;
        }
    }

    private static void ejecutarQ5() {
        System.out.println("\n--- Q5. Left join ---");
        List<Object[]> rows =  profesorDAO.leftJoinProfesorModulos();
        if (rows.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }
        int idx = 1;
        for (Object[] r : rows) {
            System.out.println(" " + idx + ". " + (r[0] != null ? r[0] : "null") + " " + (r[1] != null ? r[1] : "") + " "
                    + (r[2] != null ? r[2] : "") + " -> Modulo: " + (r[3] != null ? r[3] : "null"));
            idx++;
        }
    }

    private static void ejecutarQ6() {
        System.out.println("\n--- Q6. Join a través de asociaciones (direcciones - Galicia) ---");
        List<Direccion> direcciones = direccionDAO.direccionesPorComunidad("Galicia");
        if (direcciones.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }
        int idx = 1;
        for (Direccion d : direcciones) {
            String provincia = d.getProvincia() != null ? d.getProvincia().getNombre() : "null";
            System.out.println(" " + idx + ". ID:" + d.getId() + " | Provincia: " + provincia + " | Calle: " + d.getCalle()
                    + " | Poblacion: " + d.getPoblacion());
            idx++;
        }
    }

    private static void ejecutarQ7() {
        System.out.println("\n--- Q7. Navegación por propiedades (direcciones - Galicia) ---");
        List<Direccion> direcciones = direccionDAO.direccionesPorComunidadImplicit("Galicia");
        if (direcciones.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }
        int idx = 1;
        for (Direccion d : direcciones) {
            String provincia = d.getProvincia() != null ? d.getProvincia().getNombre() : "null";
            System.out.println(" " + idx + ". ID:" + d.getId() + " | Provincia: " + provincia + " | Calle: " + d.getCalle()
                    + " | Poblacion: " + d.getPoblacion());
            idx++;
        }
    }

    private static int leerEntero(String mensaje) {
        int valor = -1;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(scanner.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
            }
        }
        return valor;
    }

}
