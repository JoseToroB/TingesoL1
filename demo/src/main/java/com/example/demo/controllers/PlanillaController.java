package com.example.demo.controllers;
import com.example.demo.entities.PlanillaEntity;
import com.example.demo.services.PlanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping
public class PlanillaController {
    @Autowired
    PlanillaService planillaService;
    /*listar planillas de un estudiante x idEstudiante*/
    @GetMapping("/verResumen/{id}")
    public ResponseEntity<List<PlanillaEntity>> getUser(@PathVariable int id){
        planillaService.calcularPlanillaE(id);
        return new ResponseEntity<>(planillaService.obtenerPlanillasEstudiante(id), HttpStatus.OK);
    }

}
