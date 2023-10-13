package com.example.demo.controllers;
import com.example.demo.entities.CuotaEntity;
import com.example.demo.entities.PlanillaEntity;
import com.example.demo.repositories.CuotaRepository;
import com.example.demo.services.CuotaService;
import com.example.demo.entities.PruebaEntity;
import com.example.demo.services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlanillaController {
    CuotaService cuotaService;
    PruebaService pruebaService;
    /*
    generar la planilla del estudiante segun su id
     */
    @GetMapping("/calcularPlanillaEstudiante/{id}")
    public String calcularPlanillaE(@PathVariable int id){
        //obtener cuotas
        ArrayList<CuotaEntity> cuotas = new ArrayList<>();
        cuotas = cuotaService.obtenerCuotasEstudiante(id);
        //obtener pruebas
        ArrayList<PruebaEntity> pruebas = new ArrayList<>();
        pruebas = pruebaService.obtenerPruebasEstudiante(id);
        //recorrer pruebas y cuotas comparando fecha
        int cantCuota=cuotas.size();
        int cantPrueba=pruebas.size();
        int i;
        int j;
        int puntaje;
        float monto;
        for(i=0;i<cantPrueba;i++){//recorrer pruebas
            puntaje = pruebas.get(i).getPuntaje();
            for(j=0;j<cantCuota;j++){ //recorrer cuota
                monto =cuotas.get(j).getMontoApagar();
                if (Objects.equals(pruebas.get(i).getFecha(), cuotas.get(j).getFechaPago())){//si las fechas son iguales
                    //revisar los puntajes
                    if(puntaje>=950){
                        //caso 10% dcto
                        monto= (float) (monto*0.9);
                    }
                    if(puntaje>=900 && puntaje<950 ){
                        monto= (float) (monto*0.95);
                    }
                    if(puntaje>=850 && puntaje<900){
                        //caso 2% dcto
                        monto= (float) (monto*0.98);
                    }
                    //puntaje menor a 850, 0%dcto
                    //se actualiza el monto de la cuota y se marca como revisado su dcto
                }
            }
        }
            //si la fecha de la prueba corresponde entonces se actualiza el monto de la cuota mensual
        //se tiene la lista de cuotas actualizadas
            //se actualizan las cuotas y se marcan como actualizadas
        //se genera una planilla y se rellena con los datos
        //se guarda la planilla
        return "index";
    }


}
