package com.example.demo.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.EstudianteEntity;
@Repository
public interface EstudianteRepository extends CrudRepository<EstudianteEntity,Long> {
    public EstudianteEntity findById(long id);
}
