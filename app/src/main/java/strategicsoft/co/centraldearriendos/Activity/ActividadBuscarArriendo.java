package strategicsoft.co.centraldearriendos.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import strategicsoft.co.centraldearriendos.Fragment.FragmentDestacados;
import strategicsoft.co.centraldearriendos.R;

public class ActividadBuscarArriendo extends AppCompatActivity {

    Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcion_servicios_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (savedInstanceState == null) {
            arguments = getIntent().getExtras();
            arguments.putString("operador", "list_busqueda");
            arguments.putString("ciudad", arguments.getString("ciudad"));
            arguments.putString("municipio", arguments.getString("municipio"));
            arguments.putString("barrio", arguments.getString("barrio"));
            arguments.putString("tipo_negocio", arguments.getString("tipo_negocio"));
            arguments.putString("tipo_propiedad", arguments.getString("tipo_propiedad"));
            arguments.putString("estrato", arguments.getString("estrato"));
            arguments.putString("precio_ini", arguments.getString("precio_ini"));
            arguments.putString("precio_fin", arguments.getString("precio_fin"));

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.containerdesser, FragmentDestacados.newInstance(arguments))
                    .commit();
        }
    }
}
