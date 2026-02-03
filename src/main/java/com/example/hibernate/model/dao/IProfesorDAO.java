package com.example.hibernate.model.dao;

import java.util.List;

import com.example.hibernate.dto.ProfesorInfo;
import com.example.hibernate.model.Profesor;

public interface IProfesorDAO {

    void save(Profesor profesor);

    void update(Profesor profesor);

    void delete(Profesor profesor);

    Profesor findById(Integer id);

    List<Profesor> findAll();

    // Métodos extra de consultas
    List<ProfesorInfo> findAllProfesorInfo();

    List<Object[]> findIdAndNombre();

    List<Profesor> findProfesorByNombre(String nombre);

    List<Profesor> findProfesorByNombre2(String nombre);

    List<Profesor> findProfesoresByPage(int pagina, int tamanhoPagina);

    List<Profesor> findProfesorByNombreApe1Ape2(String nombre, String apellido1, String apellido2);

    List<Object[]> findProfesoresCountByName(String name);

    // Ejemplos de joins
    List<Object[]> crossJoinProfesorModulo();

    List<Object[]> crossJoinConMemberOf();

    List<Object[]> innerJoinProfesorModulos();

    List<Object[]> joinProfesorYModulo();

    List<Object[]> leftJoinProfesorModulos();

}
