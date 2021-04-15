package es.joseljg.navegacionnavigation.tareas;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import es.joseljg.navegacionnavigation.clases.Provincia;
import es.joseljg.navegacionnavigation.modelos.ProvinciaDB;


public class TareaMostrarProvincias implements Callable<ArrayList<Provincia>> {

    private ArrayList<Provincia> provincias = null;
    private int pagina;
    public TareaMostrarProvincias(int pagina) {
        this.pagina = pagina;

    }

    @Override
    public ArrayList<Provincia> call() throws Exception {
        provincias = ProvinciaDB.obtenerProvincias(this.pagina);
        return provincias;
    }
}
