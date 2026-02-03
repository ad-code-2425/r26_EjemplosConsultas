package com.example.hibernate.model.dao;

import java.util.List;

import com.example.hibernate.model.Direccion;

public interface IDireccionDAO {

    void save(Direccion direccion);

    void update(Direccion direccion);       
    void delete(Direccion direccion);

    Direccion findById(Integer id);

    List<Direccion> findAll();

    List<Direccion> direccionesPorComunidad(String nombreComunidad);

    List<Direccion> direccionesPorComunidadImplicit(String nombreComunidad);

}
