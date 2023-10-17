package com.example.demo.controllers;
import com.example.demo.entities.CuotaEntity;
import com.example.demo.services.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class CuotaController {
    @Autowired
    CuotaService cuotaService;

    @PostMapping("/crearCuota")
    public String crearCuota(@ModelAttribute("cuota") CuotaEntity cuota, Model model){
        cuotaService.guardarCuota(cuota);
        model.addAttribute("cuota",cuota);
        return "index";
    }

   @GetMapping("/listaCuotas/{id}")
    public String getUsers(@PathVariable int id,Model model) {
        List<CuotaEntity>cuotas = cuotaService.obtenerCuotasEstudiante(id);
        model.addAttribute("cuotas",cuotas);
        return "mostrarCuotas";
    }

    @GetMapping("/borrarCuota")
    public String vaciarCuotas(){
        cuotaService.borrarTodo();
        return "redirect:/";
    }
    @PostMapping("/modificarCuota/{id}")
    public String modificarCuota(@PathVariable Long id,Model model) {
        // Obtiene la cuota a modificar
        CuotaEntity cuota = cuotaService.buscarPorId(id);

        // Verifica si la cuota existe
        if (cuota != null) {
            // Modifica el parámetro deseado de la cuota en este caso, el parametro de pagado se cambia a "1"
            cuota.setEstaPagado(1);
            cuotaService.guardarCuota(cuota);
        } else {

        }
        return "index"; // Página donde mostrar el mensaje de resultado
    }
}
