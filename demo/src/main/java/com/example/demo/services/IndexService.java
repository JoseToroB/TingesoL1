package com.example.demo.services;

import com.example.demo.entities.CuotaEntity;
import com.example.demo.entities.EstudianteEntity;
import com.example.demo.entities.PruebaEntity;
import com.example.demo.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class IndexService {
    @Autowired
    CuotaService cuotaService;
    @Autowired
    EstudianteService estudianteService;
    @Autowired
    PruebaService pruebaService;
    @Autowired
    PlanillaService planillaService;
    public int vaciarBD(){
        cuotaService.borrarTodo();
        estudianteService.borrarTodo();
        planillaService.borrarTodo();
        pruebaService.borrarTodo();
        return 1;
    }
    public long poblarBD(){
        EstudianteEntity e= new EstudianteEntity();
        e.setRut("rut");
        e.setNombre("nombre");
        estudianteService.guardarEstudiante(e);
        long idE=e.getId();
        CuotaEntity c = new CuotaEntity();
        c.setIdEstudiante((int)idE);
        c.setNumeroCuota(1);
        c.setCantidadCuota(1);
        c.setMontoApagar((float)1);
        c.setMontoOriginal((float)1);
        c.setFechaPago("1-2-2023");
        c.setEstaAtrasada(0);
        c.setEstaPagado(0);
        c.setRebajada(0);
        cuotaService.guardarCuota(c);
        PruebaEntity p=new PruebaEntity();
        p.setIdEstudiante((int)idE);
        p.setPuntaje(500);
        p.setFecha("2-3-2023");
        pruebaService.guardarPrueba(p);
        return idE;

    }

}
