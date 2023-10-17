package com.example.demo.services;

import com.example.demo.entities.PruebaEntity;
import com.example.demo.repositories.PruebaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PruebaServiceTest {
    @Autowired
    PruebaRepository pruebaRepository;

    @Autowired
    PruebaService pruebaService;

    @Test
    public void obtenerPruebasTest(){
        pruebaRepository.deleteAll();
        PruebaEntity p = new PruebaEntity();
        pruebaRepository.save(p);
        ArrayList<PruebaEntity> lista = pruebaService.obtenerPruebas();
        assertEquals(1,lista.size());
        pruebaRepository.delete(p);
    }

    @Test
    public void guardarPruebaTest(){
        pruebaRepository.deleteAll();
        PruebaEntity p = new PruebaEntity();
        p.setPuntaje(727);
        pruebaService.guardarPrueba(p);
        ArrayList<PruebaEntity> lista = (ArrayList<PruebaEntity>) pruebaRepository.findAll();
        int resultado = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getPuntaje() == 727){
                resultado = 1;
            }
        }
        assertEquals(resultado, 1);
        pruebaRepository.delete(p);
    }

    @Test
    public void borrarTodoTest(){
        pruebaRepository.deleteAll();
        PruebaEntity p = new PruebaEntity();
        pruebaRepository.save(p);
        pruebaService.borrarTodo();
        ArrayList<PruebaEntity> lista = (ArrayList<PruebaEntity>) pruebaRepository.findAll();
        assertEquals(0,lista.size());

    }

    @Test
    public void obtenerPruebasEstudianteTest(){
        pruebaRepository.deleteAll();
        PruebaEntity p = new PruebaEntity();
        p.setIdEstudiante(1);
        pruebaRepository.save(p);
        ArrayList<PruebaEntity> lista = pruebaService.obtenerPruebasEstudiante(1);
        assertEquals(1,lista.size());
        pruebaRepository.delete(p);
    }
}
