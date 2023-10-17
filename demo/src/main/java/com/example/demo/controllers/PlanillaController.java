package com.example.demo.controllers;
import com.example.demo.entities.PlanillaEntity;
import com.example.demo.services.PlanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping
public class PlanillaController {
    @Autowired
    PlanillaService planillaService;
    /*listar planillas de un estudiante x idEstudiante*/
    @GetMapping("/verResumen/{id}")
    public String getUser(@PathVariable int id, Model model){
        planillaService.calcularPlanillaE(id);
        List<PlanillaEntity>planillas =planillaService.obtenerPlanillasEstudiante(id);
        model.addAttribute("planillas",planillas);
        return "mostrarResumen";
    }



}
