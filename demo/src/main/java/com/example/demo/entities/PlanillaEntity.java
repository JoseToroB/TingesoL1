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
    private int cantidadTotal;
    private int cuotasTotal;
    private int cantidadPagada;
    private int cuotasPagadas;
    private int cantidadApagar;
    private int cuotasApagar;

}
