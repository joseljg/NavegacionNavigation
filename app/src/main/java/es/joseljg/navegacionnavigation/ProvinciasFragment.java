package es.joseljg.navegacionnavigation;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import es.joseljg.navegacionnavigation.clases.ConfiguracionesGenerales;
import es.joseljg.navegacionnavigation.clases.ListaProvinciasAdapter;
import es.joseljg.navegacionnavigation.clases.Provincia;
import es.joseljg.navegacionnavigation.controladores.ProvinciaController;
import es.joseljg.navegacionnavigation.utilidades.PaginationListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProvinciasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProvinciasFragment extends Fragment {
    private static final int PETICION1 = 1;
    private RecyclerView mRecyclerView;
    private ListaProvinciasAdapter mAdapter;
    private ArrayList<Provincia> provincias;
    private int pagina_actual;
    private int total_registros;
    private int total_paginas;
    private int num_columnas_landscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista =  inflater.inflate(R.layout.fragment_provincias, container, false);
        total_registros = ProvinciaController.obtenerCantidadProvincias();
        Log.i("sql", "total registros -> " + String.valueOf(total_registros));

        total_paginas = (total_registros / ConfiguracionesGenerales.ELEMENTOS_POR_PAGINA) +  1;
        Log.i("sql", "total paginas -> " + String.valueOf(total_paginas));

        pagina_actual=0;
        num_columnas_landscape = 2;
        provincias = ProvinciaController.obtenerProvincias(pagina_actual);

        pagina_actual++;
        if(provincias != null) {
            Log.i("sql", "pagina actual -> " + String.valueOf(pagina_actual));
            Log.i("sql", "provincias leidas -> " + String.valueOf(this.provincias.size()));
            mRecyclerView = vista.findViewById(R.id.rv_provincias);
            mAdapter = new ListaProvinciasAdapter(getActivity(), provincias);
            mRecyclerView.setAdapter(mAdapter);
            int orientation = 1;
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), ConfiguracionesGenerales.LANDSCAPE_NUM_COLUMNAS));
            }

            // paginacion
            mRecyclerView.addOnScrollListener(new PaginationListener((LinearLayoutManager) mRecyclerView.getLayoutManager()) {
                private int provincias_leidas = 0;
                @Override
                protected void loadMoreItems() {

                    int total_registros_leidos = mRecyclerView.getLayoutManager().getItemCount();
                    if (total_registros_leidos < total_registros) {
                        ArrayList<Provincia> nuevasProvincias = ProvinciaController.obtenerProvincias(pagina_actual);
                        provincias_leidas = nuevasProvincias.size();
                        pagina_actual++;

                        Boolean resultado = mRecyclerView.post(new Runnable()
                        {
                            @Override
                            public void run() {
                                ListaProvinciasAdapter mAdapter1 =(ListaProvinciasAdapter) mRecyclerView.getAdapter();
                                ArrayList<Provincia> provincias_rv = mAdapter1.getListaProvincias();
                                provincias_rv.addAll(nuevasProvincias);
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            }});
                        Log.i("sql", "siguiente pagina -> " + String.valueOf(pagina_actual));
                        Log.i("sql", "total registros -> " + String.valueOf(total_registros));
                        Log.i("sql", "total registros leidos -> " + String.valueOf(total_registros_leidos));
                        Log.i("sql", "provincias leidas -> " + String.valueOf(this.provincias_leidas));

                    }
                    else{
                        provincias_leidas = 0;
                    }
                }
                @Override
                public boolean isLastPage() {
                    if(pagina_actual > total_paginas -1 ) {
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            });
        }
        else{
            mostrarToast("no se pudo establecer la conexion con la base de datos");
            Log.i("sql", "error en el sql");
        }
        return vista;
    }

    private void mostrarToast(String texto) {
        Toast.makeText(getActivity(),texto, Toast.LENGTH_SHORT).show();
    }
}