package com.example.hibernate.model.dao;

import java.util.List;
import com.example.hibernate.model.Ciclo;

public interface ICicloDAO {

    void save(Ciclo ciclo);

    void update(Ciclo ciclo);

    void delete(Ciclo ciclo);

    Ciclo findById(Integer id);

    List<Ciclo> findAll();
    //Consulta extra
    List<Object[]> findCiclosStats();

}
