package com.example.demo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import com.example.demo.services.EstudianteService;
import com.example.demo.entities.EstudianteEntity;

@Controller
@RequestMapping
public class EstudianteController {
    @Autowired
    EstudianteService estudianteService;

    @PostMapping("/crearEstudiante")
    public String crearEstudiante(@ModelAttribute("estudiante")EstudianteEntity estudiante, Model model){
        estudianteService.guardarEstudiante(estudiante);
        model.addAttribute("estudiante",estudiante);
        return "index";
    }


    @GetMapping("/listaEstudiantes")
    public String listaEstudiantes(EstudianteEntity estudiante,Model model){
        ArrayList<EstudianteEntity> estudiantes=estudianteService.obtenerEstudiantes();
        model.addAttribute("estudiante",estudiantes);
        return "listaEstudiantes";
    }

    @GetMapping("/borrarEstudiante")
    public String vaciarEstudiantes(){
        estudianteService.borrarTodo();
        return "redirect:/";
    }
}
