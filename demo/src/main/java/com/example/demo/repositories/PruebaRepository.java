package com.example.demo.repositories;

import com.example.demo.entities.PruebaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PruebaRepository extends CrudRepository<PruebaEntity,Long> {
    public ArrayList<PruebaEntity> findAllByIdEstudiante(int idEstudiante);
}
