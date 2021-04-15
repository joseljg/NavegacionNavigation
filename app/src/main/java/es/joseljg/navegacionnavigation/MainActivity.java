package es.joseljg.navegacionnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import static androidx.navigation.fragment.NavHostFragment.findNavController;
public class MainActivity extends AppCompatActivity {

    private ImageButton bt_ciudades = null;
    private ImageButton bt_provincias = null;
    private String subtitulo = "";

    public static final String EXTRA_SUBTITULO = "es.joseljg.CiudadViewHolder.subtitulo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
        } else {
        }
        bt_ciudades = (ImageButton) findViewById(R.id.bt_ciudades);
        bt_provincias = (ImageButton) findViewById(R.id.bt_provincias);

          bt_ciudades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportActionBar().setSubtitle(R.string.listaciudades);
                subtitulo = getString(R.string.listaciudades);
                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_ir_a_ciudades);
            }
        });


        bt_provincias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setSubtitle(R.string.lista_de_provincias);
                subtitulo = getString(R.string.lista_de_provincias);
                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                findNavController(navHostFragment).navigate(R.id.action_ir_a_provincias);
            }});


    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRestoreInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}