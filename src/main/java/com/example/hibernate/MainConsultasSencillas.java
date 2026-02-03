package com.example.hibernate;

import java.util.List;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import com.example.hibernate.dto.ProfesorInfo;
import com.example.hibernate.model.Profesor;
import com.example.hibernate.model.dao.CicloDAOHibernateImpl;
import com.example.hibernate.model.dao.ICicloDAO;
import com.example.hibernate.model.dao.IProfesorDAO;
import com.example.hibernate.model.dao.ProfesorDAOHibernateImpl;
import com.example.hibernate.util.HibernateUtil;

public class MainConsultasSencillas {

    private static final Scanner scanner = new Scanner(System.in);
    private static IProfesorDAO profesorDAO;
    private static ICicloDAO cicloDAO;

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        profesorDAO = new ProfesorDAOHibernateImpl(sessionFactory);
        cicloDAO = new CicloDAOHibernateImpl(sessionFactory);

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1:
                    consultaFindAllProfesorInfo();
                    break;
                case 2:
                    consultaFindIdAndNombre();
                    break;
                case 3:
                    consultaFindProfesorByNombre();
                    break;
                case 4:
                    consultaFindProfesorByNombre2();
                    break;
                case 5:
                    consultaFindProfesoresByPage();
                    break;
                case 6:
                    consultaFindProfesorByNombreApe1Ape2();
                    break;
                case 7:
                    consultaFindProfesoresCountByName();
                    break;
                case 8:
                    consultaFindCiclosStats();
                    break;
                case 0:
                    salir = true;
                    System.out.println("\nSaliendo...");
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
        System.out.println("  CONSULTAS SENCILLAS");
        System.out.println("==============================");
        System.out.println("1. findAllProfesorInfo()");
        System.out.println("2. findIdAndNombre()");
        System.out.println("3. findProfesorByNombre(String)");
        System.out.println("4. findProfesorByNombre2(String)");
        System.out.println("5. findProfesoresByPage(int, int)");
        System.out.println("6. findProfesorByNombreApe1Ape2(String, String, String)");
        System.out.println("7. findProfesoresCountByName(String)");
        System.out.println("8. findCiclosStats() [CicloDAO]");
        System.out.println("0. Salir");
        System.out.println("==============================");
    }

    // ==========================================
    // CONSULTAS DE PROFESOR
    // ==========================================

    private static void consultaFindAllProfesorInfo() {
        System.out.println("\n--- 1. findAllProfesorInfo() ---");
        try {
            List<ProfesorInfo> resultados = profesorDAO.findAllProfesorInfo();
            if (resultados.isEmpty()) {
                System.out.println("Sin resultados.");
                return;
            }
            int idx = 1;
            for (ProfesorInfo info : resultados) {
                System.out.println("  " + idx + ". " + info);
                idx++;
            }
            System.out.println("Total: " + resultados.size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void consultaFindIdAndNombre() {
        System.out.println("\n--- 2. findIdAndNombre() ---");
        try {
            List<Object[]> resultados = profesorDAO.findIdAndNombre();
            if (resultados.isEmpty()) {
                System.out.println("Sin resultados.");
                return;
            }
            int idx = 1;
            for (Object[] row : resultados) {
                System.out.println("  " + idx + ". ID: " + row[0] + " | Nombre: " + row[1]);
                idx++;
            }
            System.out.println("Total: " + resultados.size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void consultaFindProfesorByNombre() {
        System.out.println("\n--- 3. findProfesorByNombre(String nombre) ---");
        String nombre = leerString("Ingresa el nombre del profesor: ");
        try {
            List<Profesor> resultados = profesorDAO.findProfesorByNombre(nombre);
            if (resultados.isEmpty()) {
                System.out.println("Sin resultados.");
                return;
            }
            int idx = 1;
            for (Profesor p : resultados) {
                System.out.println("  " + idx + ". ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Ape1: "
                        + p.getApe1() + " | Ape2: " + p.getApe2());
                idx++;
            }
            System.out.println("Total: " + resultados.size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void consultaFindProfesorByNombre2() {
        System.out.println("\n--- 4. findProfesorByNombre2(String nombre) ---");
        String nombre = leerString("Ingresa el nombre del profesor: ");
        try {
            List<Profesor> resultados = profesorDAO.findProfesorByNombre2(nombre);
            if (resultados.isEmpty()) {
                System.out.println("Sin resultados.");
                return;
            }
            int idx = 1;
            for (Profesor p : resultados) {
                System.out.println("  " + idx + ". ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Ape1: "
                        + p.getApe1() + " | Ape2: " + p.getApe2());
                idx++;
            }
            System.out.println("Total: " + resultados.size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void consultaFindProfesoresByPage() {
        System.out.println("\n--- 5. findProfesoresByPage(int pagina, int tamanhoPagina) ---");
        int pagina = leerEntero("Número de página (ej: 0): ");
        int tamanhoPagina = leerEntero("Tamaño de página (ej: 5): ");
        try {
            List<Profesor> resultados =  profesorDAO.findProfesoresByPage(pagina,
                    tamanhoPagina);
            if (resultados.isEmpty()) {
                System.out.println("Sin resultados.");
                return;
            }
            int idx = 1;
            for (Profesor p : resultados) {
                System.out.println("  " + idx + ". ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Ape1: "
                        + p.getApe1() + " | Ape2: " + p.getApe2());
                idx++;
            }
            System.out.println("Total: " + resultados.size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void consultaFindProfesorByNombreApe1Ape2() {
        System.out.println("\n--- 6. findProfesorByNombreApe1Ape2(String, String, String) ---");
        String nombre = leerString("Ingresa el nombre: ");
        String ape1 = leerString("Ingresa el apellido 1: ");
        String ape2 = leerString("Ingresa el apellido 2: ");
        try {
            List<Profesor> resultados =  profesorDAO
                    .findProfesorByNombreApe1Ape2(nombre, ape1, ape2);
            if (resultados.isEmpty()) {
                System.out.println("Sin resultados.");
                return;
            }
            //Parece que este método no funciona correctamente con los nombres y/o apellidos que contienen tildes. Debe de haber algún problema de codificación al leer de la terminal.
            int idx = 1;
            for (Profesor p : resultados) {
                System.out.println("  " + idx + ". ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Ape1: "
                        + p.getApe1() + " | Ape2: " + p.getApe2());
                idx++;
            }
            System.out.println("Total: " + resultados.size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void consultaFindProfesoresCountByName() {
        System.out.println("\n--- 7. findProfesoresCountByName(String name) ---");
        String name = leerString("Ingresa parte del nombre a buscar: ");
        try {
            List<Object[]> resultados = profesorDAO.findProfesoresCountByName(name);
            if (resultados.isEmpty()) {
                System.out.println("Sin resultados.");
                return;
            }
            System.out.println("Resultados (nombre | cantidad):");
            int idx = 1;
            for (Object[] row : resultados) {
                System.out.println("  " + idx + ". " + row[0] + " -> Cantidad: " + row[1]);
                idx++;
            }
            System.out.println("Total de grupos: " + resultados.size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==========================================
    // CONSULTAS DE CICLO
    // ==========================================

    private static void consultaFindCiclosStats() {
        System.out.println("\n--- 8. findCiclosStats [CicloDAO] ---");
        
        try {
            List<Object[]> resultados = cicloDAO.findCiclosStats();
            if (resultados.isEmpty()) {
                System.out.println("Sin resultados.");
                return;
            }
            System.out.println("Estadísticas (AVG | SUM | MIN | MAX | COUNT):");
            int idx = 1;
            for (Object[] row : resultados) {
                System.out.println("  " + idx + ". AVG: " + row[0] + " | SUM: " + row[1] + " | MIN: " + row[2] + " | MAX: "
                        + row[3] + " | COUNT: " + row[4]);
                idx++;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================

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

    private static String leerString(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

}
