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

    //falta hacer test
    public String tipoCuota(int cantCuota){
        if(cantCuota<=0){
            return "Error";
        }
        if(cantCuota==1){
            return "Contado";
        }else{
            return "Cuotas";
        }
    }
    public float mesesAtraso(int mesActual,int mesCuota,CuotaEntity cuotaEntity){
        if(mesActual<=mesCuota){
            return (float)1;
        }
        if((mesActual-mesCuota)==1){
            //1
            cuotaEntity.setRebajada(1);
            cuotaEntity.setEstaAtrasada(1);
            return (float)1.03;
        }
        if((mesActual-mesCuota)==2){
            //2
            cuotaEntity.setRebajada(1);
            cuotaEntity.setEstaAtrasada(1);
            return (float)1.06;
        }
        if((mesActual-mesCuota)==3){
            //3
            cuotaEntity.setRebajada(1);
            cuotaEntity.setEstaAtrasada(1);
            return (float)1.09;
        }
        if((mesActual-mesCuota)>3){
            //>3
            cuotaEntity.setRebajada(1);
            cuotaEntity.setEstaAtrasada(1);
            return (float)1.15;
        }
        //si el mes actual es menor o igual al mes de la cuota, se retorna el 1
        return (float)1;
    }
    public float descuento(int puntaje,CuotaEntity cuotaEntity){
        //revisar los puntajes y aplicar dcto
        cuotaEntity.setRebajada(1);//marco ocomo realizado el dcto
        if(puntaje>=950){
            //caso 10% dcto
            return (float)0.9;
        }
        if(puntaje>=900){
            //caso 5% dcto
            return (float) 0.95;
        }
        if(puntaje>=850){
            //caso 2% dcto
            return (float) 0.98;
        }
        return (float) 1;// puntajes mejores a 850
    }
    public String calcularPlanillaE(int id){
        //obtener cuotas
        ArrayList<CuotaEntity> cuotas = cuotaService.obtenerCuotasEstudiante(id);
        //obtener pruebas
        ArrayList<PruebaEntity> pruebas = pruebaService.obtenerPruebasEstudiante(id);
        ///////////si no hay pruebas o cuotas//////
        if(cuotas.isEmpty() || pruebas.isEmpty()){ //return al index
            return "Error";
        }
        //recorrer pruebas y cuotas comparando fecha
        int cantCuota=cuotas.size();
        int cantPrueba=pruebas.size();
        int i,j,puntaje,pagadas=0,atrasada=0,mesCuota,mesPrueba;
        float montoTotal=0,monto;
        float montoTotalpagado=0;
        float montoTotalApagar=0;
        String[] fechaCuota,fechaPrueba;
        Calendar calendar = Calendar.getInstance();
        int puntajePruebas=0;
        // Obtiene el mes actual (los meses van de 0 a 11)
        int mesActual = calendar.get(Calendar.MONTH) + 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaFormateada = dateFormat.format(calendar.getTime());
        int ultimopago=0;
        String fechaUltimopago = null;
        String tipoPago=tipoCuota(cantCuota);
        for(i=0;i<cantPrueba;i++){//recorrer pruebas
            puntaje = pruebas.get(i).getPuntaje();
            puntajePruebas=puntajePruebas+puntaje;
            for(j=0;j<cantCuota;j++){ //recorrer cuota
                monto =cuotas.get(j).getMontoApagar();
                fechaCuota =cuotas.get(j).getFechaPago().split("-");// de "dd-mm-yyyy" a "dd","mm","yyyy"
                fechaPrueba = pruebas.get(i).getFecha().split("-");
                mesCuota=0;
                mesPrueba=0;
                try {
                    mesCuota = Integer.parseInt(fechaCuota[1]);
                    mesPrueba = Integer.parseInt(fechaPrueba[1]);
                } catch (NumberFormatException e) {
                    System.out.println("error al transformar mes de la cuota/prueba en int");
                    return "fallo str to int";
                }
                //si el mes de la cuota es menor al actual, esta atrasada ( el mes se inicializa con 0 ya que sino da error al compilar x el try)

                //si esta atrasada se aumenta el monto
                monto=monto*mesesAtraso(mesActual,mesCuota,cuotas.get(j));//reviso el atraso( en caso de no estarlo el retorno es un *1)
                atrasada=atrasada+cuotas.get(j).getEstaAtrasada();//si quedo marcada como atrasda, se suma 1

                //si la cuota no esta pagada y no esta atrasada, reviso su dcto x prueba
                if((cuotas.get(j).getEstaPagado()==0 && cuotas.get(j).getEstaAtrasada()==0)&&(mesPrueba==mesCuota)){
                    //si no esta pagada y no esta atrasada
                    // revisar los puntajes y aplicar dcto
                    monto=monto*descuento(puntaje,cuotas.get(j));
                    montoTotalApagar=montoTotalApagar+monto;
                }
                if(cuotas.get(j).getEstaPagado()==1){
                    pagadas++;//si esta pagada, se aumenta este contador para facilitar calculos futuros
                    if(ultimopago<mesCuota){//guardo el mes de la ultima cuota pagada
                        ultimopago=mesCuota;
                        fechaUltimopago=cuotas.get(j).getFechaPago();
                    }
                    montoTotalpagado=montoTotalpagado+monto;//se aumenta el monto de pagado
                }
                cuotas.get(j).setMontoApagar(monto);
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
