package com.example.demo.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest

public class IndexServiceTest {
    @Autowired
    IndexService indexService;
    @Test
    public void poblarBDTest(){
        Long i= indexService.poblarBD();
        assertTrue(i instanceof Long);
        indexService.vaciarBD();
    }
    @Test
    public void vaciarBDTest(){
        assertEquals(1,indexService.vaciarBD());
    }

}
