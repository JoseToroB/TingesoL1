package com.example.demo.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.CuotaEntity;
import com.example.demo.repositories.CuotaRepository;
import java.util.ArrayList;
@Service
public class CuotaService {
    @Autowired
    CuotaRepository cuotaRepository;
    public ArrayList<CuotaEntity> obtenerCuotas(){
        return (ArrayList<CuotaEntity>) cuotaRepository.findAll();
    }

    public void guardarCuota(CuotaEntity cuota){
        cuotaRepository.save(cuota);
    }
    public void borrarTodo(){
        cuotaRepository.deleteAll();
    }
    public ArrayList<CuotaEntity> obtenerCuotasEstudiante (int idEstudiante){
        ArrayList<CuotaEntity> cuotas = cuotaRepository.findAllByIdEstudiante(idEstudiante);
        return cuotas;
    }
    public CuotaEntity buscarPorId(long id){return cuotaRepository.findById(id);}

}
