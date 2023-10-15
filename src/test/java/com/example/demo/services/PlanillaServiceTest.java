package com.example.demo.services;
import com.example.demo.entities.CuotaEntity;
import com.example.demo.entities.EstudianteEntity;
import com.example.demo.entities.PlanillaEntity;
import com.example.demo.entities.PruebaEntity;
import com.example.demo.repositories.CuotaRepository;
import com.example.demo.repositories.EstudianteRepository;
import com.example.demo.repositories.PlanillaRepository;
import com.example.demo.repositories.PruebaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

public class PlanillaServiceTest {
    @Autowired
    PlanillaRepository planillaRepository;
    @Autowired
    PlanillaService planillaService;
    @Autowired
    CuotaService cuotaService;
    @Autowired
    PruebaService pruebaService;
    @Autowired
    CuotaRepository cuotaRepository;
    @Autowired
    PruebaRepository pruebaRepository;
    @Autowired
    EstudianteService estudianteService;
    @Autowired
    EstudianteRepository estudianteRepository;
    @Test
    void obtenerPlanillasTest(){
        PlanillaEntity planilla = new PlanillaEntity();
        planillaRepository.save(planilla);
        ArrayList<PlanillaEntity>lista = planillaService.obtenerPlanillas();
        assertNotNull(lista);
        planillaRepository.delete(planilla);
    }
    @Test
    void guardarPlanillaTest(){
        PlanillaEntity planilla = new PlanillaEntity();
        planillaService.guardarPlanilla(planilla);
        ArrayList<PlanillaEntity>lista = planillaService.obtenerPlanillas();
        assertNotNull(lista);
        planillaRepository.delete(planilla);
    }
    @Test
    void borrarTodoTest(){

        PlanillaEntity planilla1 = new PlanillaEntity();
        planillaRepository.save(planilla1);
        PlanillaEntity planilla2 = new PlanillaEntity();
        planillaRepository.save(planilla2);
        planillaService.borrarTodo();
        ArrayList<PlanillaEntity>lista = planillaService.obtenerPlanillas();
        assertEquals(0,lista.size());
    }
    @Test
    void obtenerPlanillasEstudianteTest(){
        PlanillaEntity planilla = new PlanillaEntity();
        planilla.setIdEstudiante(727);
        planillaService.guardarPlanilla(planilla);
        ArrayList<PlanillaEntity>lista = planillaService.obtenerPlanillasEstudiante(727);
        assertNotNull(lista);
        planillaRepository.delete(planilla);
    }

    @Test
    void tipoCuotaTestA(){
        assertEquals("Error",planillaService.tipoCuota(0));
    }
    @Test
    void tipoCuotaTestB(){
        assertEquals("Contado",planillaService.tipoCuota(1));
    }
    @Test
    void tipoCuotaTestC(){
        assertEquals("Cuotas",planillaService.tipoCuota(2));
    }




    @Test
    void mesesAtrasoTestA(){
        CuotaEntity cuota = new CuotaEntity();
        assertEquals((float) 1,planillaService.mesesAtraso(1,2,cuota));
    }
    @Test
    void mesesAtrasoTestB(){
        CuotaEntity cuota = new CuotaEntity();
        assertEquals((float) 1.03,planillaService.mesesAtraso(2,1,cuota));
    }
    @Test
    void mesesAtrasoTestC(){
        CuotaEntity cuota = new CuotaEntity();
        assertEquals((float) 1.06,planillaService.mesesAtraso(3,1,cuota));
    }
    @Test
    void mesesAtrasoTestD(){
        CuotaEntity cuota = new CuotaEntity();
        assertEquals((float) 1.09,planillaService.mesesAtraso(4,1,cuota));
    }
    @Test
    void mesesAtrasoTestE(){
        CuotaEntity cuota = new CuotaEntity();
        assertEquals((float) 1.15,planillaService.mesesAtraso(5,1,cuota));
    }



    @Test
    void descuentoTestA(){
        CuotaEntity cuota = new CuotaEntity();
        assertEquals((float)0.9,planillaService.descuento(1000,cuota));
    }
    @Test
    void descuentoTestB(){
        CuotaEntity cuota = new CuotaEntity();
        assertEquals((float)0.95,planillaService.descuento(925,cuota));
    }
    @Test
    void descuentoTestC(){
        CuotaEntity cuota = new CuotaEntity();
        assertEquals((float)0.98,planillaService.descuento(875,cuota));
    }
    @Test
    void descuentoTestD(){
        CuotaEntity cuota = new CuotaEntity();
        assertEquals((float)1,planillaService.descuento(800,cuota));
    }



    @Test
    void calcularPlanillaETestA(){
        // sin pruebas ni cuotas de estudiante
        pruebaService.borrarTodo();
        cuotaService.borrarTodo();
        assertEquals("Error",planillaService.calcularPlanillaE(1));
    }
    @Test
    void calcularPlanillaETestB(){
        pruebaRepository.deleteAll();
        cuotaRepository.deleteAll();
        planillaRepository.deleteAll();

        //planilla con cuota atrasda
        EstudianteEntity estudiante= new EstudianteEntity();
        estudiante.setNombre("nombre");
        estudiante.setRut("rut");
        estudianteRepository.save(estudiante);
        long i=estudiante.getId();
        int idEstudiante = (int) i;

        PruebaEntity prueba= new PruebaEntity();
        prueba.setIdEstudiante(idEstudiante);
        prueba.setFecha("14-10-2023");
        pruebaRepository.save(prueba);

        CuotaEntity cuota =new CuotaEntity();
        cuota.setIdEstudiante(idEstudiante);
        cuota.setFechaPago("1-1-2023");
        cuota.setMontoApagar(1);
        cuotaRepository.save(cuota);

        planillaService.borrarTodo();//me aseguro que no tenga mas planillas
        planillaService.calcularPlanillaE(idEstudiante);//creo la planilla
        ArrayList<PlanillaEntity>planillas= planillaService.obtenerPlanillasEstudiante(idEstudiante);//obtengo la lista de planillas del estudiante
        //la diferencia en el monto es la que indica si esta atrasada o no
        //al estar atrasada debe ser (float)1.15

        assertEquals((float)1.15,planillas.get(0).getCantidadApagar());

        estudianteRepository.delete(estudiante);
        pruebaRepository.delete(prueba);
        cuotaRepository.delete(cuota);
    }
    @Test
    void calcularPlanillaETestC(){
        pruebaRepository.deleteAll();
        cuotaRepository.deleteAll();
        planillaRepository.deleteAll();
        //planilla con cuota sin pagar, sin atraso y los meses corresponden
        EstudianteEntity estudiante= new EstudianteEntity();
        estudiante.setNombre("nombre");
        estudiante.setRut("rut");
        estudianteRepository.save(estudiante);
        long i=estudiante.getId();
        int idEstudiante = (int) i;

        PruebaEntity prueba= new PruebaEntity();
        prueba.setIdEstudiante(idEstudiante );
        prueba.setFecha("14-10-2023");
        pruebaRepository.save(prueba);

        CuotaEntity cuota =new CuotaEntity();
        cuota.setIdEstudiante(idEstudiante);
        cuota.setFechaPago("1-10-2023");
        cuota.setMontoApagar(1);
        cuotaRepository.save(cuota);

        //el mes es el mismo, por ende se revisa
        planillaService.borrarTodo();//me aseguro que no tenga mas planillas
        planillaService.calcularPlanillaE(idEstudiante);//creo la planilla
        ArrayList<PlanillaEntity>planillas= planillaService.obtenerPlanillasEstudiante(idEstudiante);//obtengo la lista de planillas del estudiante
        //la diferencia en el monto es la que indica si esta atrasada o no
        //al estar atrasada debe ser (float)1
        assertEquals((float)1,planillas.get(0).getCuotasApagar());
        estudianteRepository.delete(estudiante);
        pruebaRepository.delete(prueba);
        cuotaRepository.delete(cuota);
    }
    @Test
    void calcularPlanillaETestD(){
        pruebaRepository.deleteAll();
        cuotaRepository.deleteAll();
        planillaRepository.deleteAll();
        //planilla con cuota pagada
        EstudianteEntity estudiante= new EstudianteEntity();
        estudiante.setNombre("nombre");
        estudiante.setRut("rut");
        estudianteRepository.save(estudiante);

        long i=estudiante.getId();
        int idEstudiante = (int) i;
        PruebaEntity prueba= new PruebaEntity();
        prueba.setIdEstudiante(idEstudiante);
        prueba.setFecha("14-10-2023");
        pruebaRepository.save(prueba);

        CuotaEntity cuota =new CuotaEntity();
        cuota.setIdEstudiante(idEstudiante);
        cuota.setFechaPago("1-1-2023");
        cuota.setEstaPagado(1);
        cuotaRepository.save(cuota);

        planillaService.borrarTodo();//me aseguro que no tenga mas planillas
        planillaService.calcularPlanillaE(idEstudiante);//creo la planilla

        ArrayList<PlanillaEntity>planillas= planillaService.obtenerPlanillasEstudiante(idEstudiante);//obtengo la lista de planillas del estudiante
        //si la cuota esta pagada la planilla lleva un conteo de  cuantas
        //cuotas pagas tiene el estudiante, ademas de la fecha de la ultima pagada
        assertEquals(1,planillas.get(0).getCuotasPagadas());
        estudianteRepository.delete(estudiante);
        pruebaRepository.delete(prueba);
        cuotaRepository.delete(cuota);
    }
}
