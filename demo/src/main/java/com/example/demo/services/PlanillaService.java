package com.example.demo.services;
import com.example.demo.entities.CuotaEntity;
import com.example.demo.entities.PlanillaEntity;
import com.example.demo.entities.PruebaEntity;
import com.example.demo.repositories.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

@Service
public class PlanillaService {
    @Autowired
    PlanillaRepository planillaRepository;
    @Autowired
    CuotaService cuotaService;
    @Autowired
    PruebaService pruebaService;
    @Autowired
    EstudianteService estudianteService; //para obtener nombre, rut
    public ArrayList<PlanillaEntity> obtenerPlanillas(){
        return (ArrayList<PlanillaEntity>) planillaRepository.findAll();
    }
    public void guardarPlanilla(PlanillaEntity planilla){
        planillaRepository.save(planilla);
    }

    public void borrarTodo() {
        planillaRepository.deleteAll();
    }
    public ArrayList<PlanillaEntity> obtenerPlanillasEstudiante(int idEstudiante){
        ArrayList<PlanillaEntity> planillas = planillaRepository.findAllByIdEstudiante(idEstudiante);
        return planillas;
    }
    public String calcularPlanillaE(int id){
        //obtener cuotas
        ArrayList<CuotaEntity> cuotas = cuotaService.obtenerCuotasEstudiante(id);
        //obtener pruebas
        ArrayList<PruebaEntity> pruebas = pruebaService.obtenerPruebasEstudiante(id);
        ///////////si no hay pruebas o cuotas//////
        if(cuotas==null || pruebas==null){ //return al index
            return "index";
        }
        //recorrer pruebas y cuotas comparando fecha
        int cantCuota=cuotas.size();
        int cantPrueba=pruebas.size();
        if(cantPrueba==0 || cantCuota==0){///////////si no hay pruebas o cuotas//////v2
            return "index";
        }
        int i,j,puntaje,pagadas=0,atrasada=0,mesCuota;
        float montoTotal=0,monto;
        float montoTotalpagado=0;
        float montoTotalApagar=0;
        String[] fechaCuota;
        Calendar calendar = Calendar.getInstance();
        int puntajePruebas=0;
        // Obtiene el mes actual (los meses van de 0 a 11)
        int mesActual = calendar.get(Calendar.MONTH) + 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaFormateada = dateFormat.format(calendar.getTime());
        int ultimopago=0;
        String fechaUltimopago = null;
        String tipoPago=null;
        if(cantCuota==1){//no deberia llegar hasta aqui en caso de no tener cuotas
            tipoPago="Contado";
        }else{
            tipoPago="Cuotas";
        }
        for(i=0;i<cantPrueba;i++){//recorrer pruebas
            puntaje = pruebas.get(i).getPuntaje();
            puntajePruebas=puntajePruebas+puntaje;
            for(j=0;j<cantCuota;j++){ //recorrer cuota
                monto =cuotas.get(j).getMontoApagar();
                fechaCuota =cuotas.get(j).getFechaPago().split("-");// de "dd-mm-yyyy" a "dd","mm","yyyy"
                mesCuota=0;
                try {
                    mesCuota = Integer.parseInt(fechaCuota[1]);
                } catch (NumberFormatException e) {
                    System.out.println("error al transformar mes de la cuota en int");
                }
                //si el mes de la cuota es menor al actual, esta atrasada ( el mes se inicializa con 0 ya que sino da error al compilar x el try)
                if((mesCuota<mesActual) && mesCuota!=0){
                    atrasada++;//contador para calculos futuros
                    if((mesActual-mesCuota)==1){
                        //1
                        monto=(float) (monto*1.03);
                    }
                    if((mesActual-mesCuota)==2){
                        //2
                        monto=(float) (monto*1.06);
                    }
                    if((mesActual-mesCuota)==3){
                        //3
                        monto=(float) (monto*1.09);
                    }
                    if((mesActual-mesCuota)>3){
                        //>3
                        monto=(float) (monto*1.15);
                    }
                    //marco la cuota como atrasada (no aplica dcto x ptje)
                    cuotas.get(j).setEstaAtrasada(1);
                    cuotas.get(j).setMontoApagar(monto);
                    cuotas.get(j).setRebajada(1);//se hizo su dcto/aumento
                }
                //si la cuota no esta pagada y no esta atrada, reviso su dcto x prueba
                if(cuotas.get(j).getEstaPagado()==0 && cuotas.get(j).getEstaAtrasada()==0){
                    //si no esta pagada y no esta atrasada
                    if (Objects.equals(pruebas.get(i).getFecha(), cuotas.get(j).getFechaPago())){//si las fechas son iguales
                        //revisar los puntajes y aplicar dcto
                        if(puntaje>=950){
                            //caso 10% dcto
                            monto= (float) (monto*0.9);
                        }
                        if(puntaje>=900 && puntaje<950 ){
                            //caso 5% dcto
                            monto= (float) (monto*0.95);
                        }
                        if(puntaje>=850 && puntaje<900){
                            //caso 2% dcto
                            monto= (float) (monto*0.98);
                        }
                        //puntaje menor a 850, 0%dcto
                        montoTotalApagar=montoTotalApagar+monto;
                        //montoTotalpagado=montoTotalpagado+monto;
                        cuotas.get(j).setMontoApagar(monto);
                        cuotas.get(j).setRebajada(1);//se hizo su dcto/aumento
                    }
                }
                if(cuotas.get(j).getEstaPagado()==1){
                    pagadas++;//si esta pagada, se aumenta este contador para facilitar calculos futuros
                    if(ultimopago<mesCuota){//guardo el mes de la ultima cuota pagada
                        ultimopago=mesCuota;
                        fechaUltimopago=cuotas.get(j).getFechaPago();
                    }
                    montoTotalpagado=montoTotalpagado+monto;//se aumenta el monto de pagado
                }
                //como cada if actualiza lacuota, actualizo el monto total final a pagar
                montoTotal=montoTotal+monto;
            }
        }//se revisan todos las pruebas/cuotas, se tiene la lista de cuotas actualizadas
        // se actualizan las cuotas y se marcan como actualizadas
        //se genera una planilla y se rellena con los datos

        int promedio=(puntajePruebas/cantPrueba);

        PlanillaEntity planillaEntity= new PlanillaEntity();
        //esto es en caso de necesitarlo para calculos futuros
        planillaEntity.setIdEstudiante(id);
        planillaEntity.setCantidadTotal(montoTotal);
        planillaEntity.setCuotasApagar(cantCuota-pagadas);
        planillaEntity.setFechaCreacionPlanilla(fechaFormateada);
        //////////////////////////////////////////////////////
        //Esto es lo que se muestra al usuario en el reporte
        planillaEntity.setNombreEstudiante(estudianteService.obtenerNombreEstudiante(id));
        planillaEntity.setRutEstudiante(estudianteService.obtenerRutEstudiante(id));
        planillaEntity.setCantidadPruebasRendidas(cantPrueba);
        planillaEntity.setPromedioPruebas(promedio);
        planillaEntity.setTipoPago(tipoPago);
        planillaEntity.setFechaUltimoPago(fechaUltimopago);
        planillaEntity.setCuotasTotal(cantCuota);
        planillaEntity.setCantidadPagada(montoTotalpagado);
        planillaEntity.setCuotasPagadas(pagadas);
        planillaEntity.setCantidadApagar(montoTotal-montoTotalpagado);
        //se guarda la planilla
        planillaRepository.save(planillaEntity);
        //se direcciona al index
        return "index";
    }
}
