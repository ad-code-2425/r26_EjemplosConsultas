package com.example.hibernate;

import java.util.List;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import com.example.hibernate.model.Ciclo;
import com.example.hibernate.model.dao.CicloDAOHibernateImpl;
import com.example.hibernate.model.dao.ICicloDAO;
import com.example.hibernate.util.HibernateUtil;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static ICicloDAO cicloDAO;

    public static void main(String[] args) {

        // Inicializar SessionFactory y DAO
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        cicloDAO = new CicloDAOHibernateImpl(sessionFactory);

        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case 1:
                    listarCiclos();
                    break;
             
                case 0:
                    salir = true;
                    System.out.println("Saliendo de la aplicación...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
        sessionFactory.close();
    }

    // =========================
    // MENÚ
    // =========================

    private static void mostrarMenu() {
        System.out.println("\n==============================");
        System.out.println(" GESTIÓN DE CICLOS (Hibernate)");
        System.out.println("==============================");
        System.out.println("1. Listar ciclos");
        // System.out.println("2. Crear ciclo");
        // System.out.println("3. Actualizar ciclo por ID");
        // System.out.println("4. Eliminar ciclo por ID");
        // System.out.println("5. Consultar ciclo por ID");
        System.out.println("0. Salir");
        System.out.println("==============================");
    }

    // =========================
    // OPERACIONES
    // =========================

    private static void listarCiclos() {
        System.out.println("\n--- LISTADO DE CICLOS ---");
        List<Ciclo> ciclos = cicloDAO.findAll();
        if (ciclos.isEmpty()) {
            System.out.println("No hay ciclos registrados.");
        } else {
            for (Ciclo c : ciclos) {
                mostrarCiclo(c);
            }
        }
    }

   



    // =========================
    // MÉTODOS AUXILIARES
    // =========================

    private static void mostrarCiclo(Ciclo c) {
        System.out.println("ID: " + c.getIdCiclo() +
                " | Nombre: " + c.getNombre() +
                " | Horas: " + c.getHoras());
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
