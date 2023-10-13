package com.example.demo.repositories;

import com.example.demo.entities.CuotaEntity;
import org.springframework.data.repository.CrudRepository;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

public interface CuotaRepository extends CrudRepository<CuotaEntity,Long> {
    public ArrayList<CuotaEntity> findAllByIdEstudiante(int idEstudiante);
    public CuotaEntity findById(long id);

}
