package com.example.demo.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.PruebaEntity;
import com.example.demo.repositories.PruebaRepository;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import lombok.Generated;
@Service
public class PruebaService {
    @Autowired
    PruebaRepository pruebaRepository;
    PruebaEntity pruebaEntity;
    private File archivo;
    @Generated
    public void save(MultipartFile file){
        if(!file.isEmpty()){
            try{
                // guardar datos y direccion
                byte [] bytes= file.getBytes();
                Path path = Paths.get(file.getOriginalFilename());
                Files.write(path,bytes);
            }catch(IOException e){
                throw new RuntimeException(e);
            }
        }
        archivo = new File(file.getOriginalFilename());
        try{
            Scanner obj = new Scanner(archivo);
            while(obj.hasNextLine()){ //mientras queden lineas
                // new
                pruebaEntity = new PruebaEntity(); //creo pruebas ( notas)
                String[] datos = obj.nextLine().split(";"); // split
                //sets idestudiante;puntaje;fecha
                try {
                    int entero = Integer.parseInt(datos[0]);
                    int puntaje = Integer.parseInt(datos[1]);
                    pruebaEntity.setIdEstudiante(entero);
                    pruebaEntity.setPuntaje(puntaje);
                } catch (NumberFormatException e) {
                    System.out.println("error al leer csv.");
                }
                pruebaEntity.setFecha(datos[2]);
                // .save
                pruebaRepository.save(pruebaEntity);
            }
            obj.close(); //se cierra el archivo
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<PruebaEntity> obtenerPruebas(){
        return (ArrayList<PruebaEntity>) pruebaRepository.findAll();
    }

    public void guardarPrueba(PruebaEntity prueba){
        pruebaRepository.save(prueba);
    }
    public void borrarTodo(){
        pruebaRepository.deleteAll();
    }
    public ArrayList<PruebaEntity>obtenerPruebasEstudiante (int idEstudiante){
        return pruebaRepository.findAllByIdEstudiante(idEstudiante);
    }
}
