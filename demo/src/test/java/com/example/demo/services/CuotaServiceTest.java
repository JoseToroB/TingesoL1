package com.example.demo.services;

import com.example.demo.entities.CuotaEntity;
import com.example.demo.repositories.CuotaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CuotaServiceTest {
    @Autowired
    CuotaService cuotaService;

    @Autowired
    CuotaRepository cuotaRepository;

    @Test
    public void obtenerCuotasTest(){
        CuotaEntity c = new CuotaEntity();
        cuotaRepository.save(c);
        ArrayList<CuotaEntity> lista = cuotaService.obtenerCuotas();
        assertEquals(1,lista.size());
        cuotaRepository.delete(c);
    }

    @Test
    public void borrarTodoTest(){
        CuotaEntity c = new CuotaEntity();
        cuotaRepository.save(c);
        cuotaService.borrarTodo();
        ArrayList<CuotaEntity> lista = (ArrayList<CuotaEntity>) cuotaRepository.findAll();
        assertEquals(0,lista.size());

    }

    @Test
    public void obtenerCuotasEstudiante(){
        cuotaRepository.deleteAll();
        CuotaEntity c = new CuotaEntity();
        c.setIdEstudiante(1);
        cuotaRepository.save(c);
        ArrayList<CuotaEntity> lista = cuotaService.obtenerCuotasEstudiante(1);
        assertEquals(1,lista.size());
        cuotaRepository.delete(c);

    }

    @Test
    public void buscarPorIdTest(){
        cuotaRepository.deleteAll();
        CuotaEntity c = new CuotaEntity();
        c.setFechaPago("fecha");
        cuotaRepository.save(c);
        assertEquals("fecha",cuotaService.buscarPorId(c.getId()).getFechaPago());
        cuotaRepository.delete(c);
    }

}
