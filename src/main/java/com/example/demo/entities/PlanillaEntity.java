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
    private String fechaCreacionPlanilla;//en caso de tener mas planillas, para saber cuando se genero cada una
    /* esto se solicita en la planilla a mostrar en HU7 */
    private String nombreEstudiante;//funcion creada
    private String rutEstudiante;//funcion creada
    private int cantidadPruebasRendidas;//cant prueba
    private int promedioPruebas;//promedio
    private String tipoPago;//if cantCuotas=1 es contado si es mas de 1 es cuotas
    private String fechaUltimoPago;//fechaUltimopago
    private int cuotasTotal; //cantCuotas
    private float cantidadPagada; // Monto total pagado
    private int cuotasPagadas; //pagadas
    private float cantidadApagar; //montoTotal-montoTotalpagado

}
