package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "estudiante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String apellido;
    private String nombre;
    private String rut;
    private String fechaNacimiento; //dd-mm-yyyy
    private String nombreColegio;
    private int anioEgreso;
    private int tipoColegio; //1 muni - 2 sub  - 3 priv
}
