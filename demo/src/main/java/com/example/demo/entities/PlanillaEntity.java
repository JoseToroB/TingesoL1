package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "planilla")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaEntity {
    /*planilla de cuotas*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private int idEstudiante;
    private float cantidadTotal;
    private int cuotasApagar;
    private String fechaCreacionPlanilla;
    private String nombreEstudiante;
    private String rutEstudiante;
    private int cantidadPruebasRendidas;
    private int promedioPruebas;
    private String tipoPago;
    private String fechaUltimoPago;
    private int cuotasTotal;
    private float cantidadPagada;
    private int cuotasPagadas;
    private float cantidadApagar;

}
