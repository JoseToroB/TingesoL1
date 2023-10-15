package com.example.demo.services;


import com.example.demo.entities.EstudianteEntity;
import com.example.demo.repositories.EstudianteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EstudianteServiceTest {
    @Autowired
    EstudianteRepository estudianteRepository;

    @Autowired
    EstudianteService estudianteService;

    @Test
    public void obtenerEstudiantesTest(){
        estudianteService.borrarTodo();
        EstudianteEntity e = new EstudianteEntity();
        estudianteRepository.save(e);
        ArrayList<EstudianteEntity> estudiantes = estudianteService.obtenerEstudiantes();
        assertNotNull(estudiantes);
        estudianteRepository.delete(e);

    }

    @Test
    public void guardarEstudianteTest(){
        estudianteService.borrarTodo();
        EstudianteEntity e = new EstudianteEntity();
        e.setNombre("test");
        estudianteService.guardarEstudiante(e);
        int resultado = 0;
        ArrayList<EstudianteEntity> lista = estudianteService.obtenerEstudiantes();
        for (int i = 0; i < lista.size(); i++){
            if (lista.get(i).getNombre().equals("test")){
                resultado = 1;
            }
        }
        assertEquals(1,resultado);
        estudianteRepository.delete(e);
    }

    @Test
    public void borrarTodoTest(){
        EstudianteEntity e = new EstudianteEntity();
        estudianteRepository.save(e);
        estudianteService.borrarTodo();
        ArrayList<EstudianteEntity> lista = (ArrayList<EstudianteEntity>) estudianteRepository.findAll();
        assertEquals(0,lista.size());

    }

    @Test
    public void obtenerNombreEstudianteTest(){
        estudianteService.borrarTodo();
        EstudianteEntity e = new EstudianteEntity();
        e.setNombre("test");
        estudianteRepository.save(e);
        int funciono = 0;
        ArrayList<EstudianteEntity>l=estudianteService.obtenerEstudiantes();
        for(int i=0;i<estudianteService.obtenerEstudiantes().size();i++) {
            if(l.get(i).getNombre().equals("test")){
               funciono=1; 
            }
        }
        assertEquals(1,funciono);
        estudianteRepository.delete(e);
    }

    @Test
    public void obtenerRutEstudiante(){
        estudianteService.borrarTodo();
        EstudianteEntity e = new EstudianteEntity();
        e.setRut("727");
        estudianteRepository.save(e);
        int funciono = 0;
        ArrayList<EstudianteEntity>l=estudianteService.obtenerEstudiantes();
        for(int i=0;i<estudianteService.obtenerEstudiantes().size();i++) {
            if(l.get(i).getRut().equals("727")){
                funciono=1;
            }
        }
        assertEquals(1,funciono);
        estudianteRepository.delete(e);
    }


}
