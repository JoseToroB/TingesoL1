package com.example.demo.repositories;

import com.example.demo.entities.PlanillaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PlanillaRepository extends CrudRepository<PlanillaEntity,Long> {
    public ArrayList<PlanillaEntity> findAllByIdEstudiante(int id);
}
