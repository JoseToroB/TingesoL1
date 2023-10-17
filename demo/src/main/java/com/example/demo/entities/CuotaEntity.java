package com.example.demo.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cuota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private int idEstudiante;
    private int numeroCuota;//numero de esta cuota     1 de
    private int cantidadCuota;//cantidad de cuotas       3
    private float montoOriginal;
    private float montoApagar;
    private String fechaPago;//dd-mm-yyyy
    private int estaAtrasada;
    private int estaPagado;
    private int rebajada;//indica si ya se aplico su dcto correspondiente
}
