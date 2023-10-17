package com.example.demo.controllers;
import com.example.demo.entities.CuotaEntity;
import com.example.demo.entities.PruebaEntity;
import com.example.demo.services.*;
import com.example.demo.entities.EstudianteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class indexController {
    @Autowired
    IndexService indexService;
    @Autowired
    CuotaService cuotaService;
    @Autowired
    EstudianteService estudianteService;
    @Autowired
    PlanillaService planillaService;
    @Autowired
    PruebaService pruebaService;
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

    @GetMapping ("/limpiarBD")
    public String limpiarBaseDatos(Model model){
        indexService.vaciarBD();
        model.addAttribute("msg","BD Vaciada");
        return "index";
    }
    @GetMapping("/poblarBD")
    public String poblarBaseDatos(Model model){
        long idE=indexService.poblarBD();
        model.addAttribute("msg1","BD poblada-idE:"+idE);
        return "index";
    }

}