package com.example.demo.controllers;
import com.example.demo.entities.CuotaEntity;
import com.example.demo.entities.EstudianteEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class indexController {
    @GetMapping("/")
    public String main(){
        return "index";
    }

    @GetMapping("/estudiante")
    public String estudiante(EstudianteEntity estudiante, Model model){
        model.addAttribute("estudiante",estudiante);
        return "crearEstudiante";
    }
    @GetMapping("/cuotas")
    public String cuotas(CuotaEntity cuotas, Model model){
        model.addAttribute("cuota",cuotas);
        return "crearCuota";
    }
    @GetMapping("/listarCuotasPorAlumno")
    public String listarCuotasAlumno(){
        return "listaCuotas";
    }

    @GetMapping ("/registrarPagoCuota")
    public String registrarPagoCuota(){return "pagoCuota"; }

    @GetMapping("/cargarExcel")
    public String cargarExcelNotas(){ return "cargarNotas";}

    @GetMapping("/calcularPlanillaPagos")
    public String calcularPlanillaPago(){return "calcularPlanilla";}

    @GetMapping("/calcularReporte")
    public String calcularReporte(){return "calcularReporte";}

}