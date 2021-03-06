package es.joseljg.navegacionnavigation.tareas;
import java.util.concurrent.Callable;

import es.joseljg.navegacionnavigation.modelos.CiudadDB;


public class TareaCantidadCiudades implements Callable<Integer> {
    private int cantidadCiudades = 0;

    @Override
    public Integer call() throws Exception {
        cantidadCiudades = CiudadDB.obtenerCantidadCiudades();
        return cantidadCiudades;
    }
}
