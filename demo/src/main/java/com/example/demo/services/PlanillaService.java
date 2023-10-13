package com.example.demo.services;
import com.example.demo.entities.PlanillaEntity;
import com.example.demo.repositories.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
@Service
public class PlanillaService {
    @Autowired
    PlanillaRepository planillaRepository;
    public ArrayList<PlanillaEntity> obtenerPlanillas(){
        return (ArrayList<PlanillaEntity>) planillaRepository.findAll();
    }
    public void guardarPlanilla(PlanillaEntity planilla){
        planillaRepository.save(planilla);
    }

    public void borrarTodo() {
        planillaRepository.deleteAll();
    }
}
