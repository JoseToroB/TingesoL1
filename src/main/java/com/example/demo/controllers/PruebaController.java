package com.example.demo.controllers;
import com.example.demo.entities.PruebaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import com.example.demo.services.PruebaService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class PruebaController {
    @Autowired
    PruebaService pruebaService;

    @PostMapping("/crearPrueba")
    public String crearPrueba(@ModelAttribute("prueba") PruebaEntity prueba, Model model){
        pruebaService.guardarPrueba(prueba);
        model.addAttribute("prueba",prueba);
        return "index";
    }
    @GetMapping("/listaPruebas")
    public String listaEstudiantes(PruebaEntity prueba, Model model){
        ArrayList<PruebaEntity> pruebas=pruebaService.obtenerPruebas();
        model.addAttribute("pruebas",pruebas);
        return "listaPruebas";
    }
    @GetMapping("/borrarPruebas")
    public String vaciarPruebas(){
        pruebaService.borrarTodo();
        return "redirect:/";
    }
    @PostMapping("/subirNotas")
    public String cargarExcel(@RequestParam("prueba") MultipartFile file, RedirectAttributes ms){
        pruebaService.save(file);
        ms.addFlashAttribute("mensaje","Archivo subido!");
        return "index";
    }

}
