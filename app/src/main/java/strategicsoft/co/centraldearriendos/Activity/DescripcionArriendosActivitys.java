package strategicsoft.co.centraldearriendos.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import strategicsoft.co.centraldearriendos.Fragment.FragmentContenidoDescripcion;
import strategicsoft.co.centraldearriendos.R;

public class DescripcionArriendosActivitys extends AppCompatActivity {

    Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcion_arriendos_activity);

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
            arguments.putInt("id_arriendo", arguments.getInt("id_arriendo"));
            arguments.putString("ciudad", arguments.getString("ciudad"));
            arguments.putString("barrio", arguments.getString("barrio"));
            arguments.putString("tipologia", arguments.getString("tipologia"));
            arguments.putString("propiedad", arguments.getString("propiedad"));
            arguments.putDouble("precio", arguments.getDouble("precio"));
            arguments.putInt("habitaciones", arguments.getInt("habitaciones"));
            arguments.putInt("banos", arguments.getInt("banos"));
            arguments.putString("area", arguments.getString("area"));
            arguments.putInt("estrato", arguments.getInt("estrato"));
            arguments.putDouble("admin", arguments.getDouble("admin"));
            arguments.putString("contacto", arguments.getString("contacto"));
            arguments.putString("email", arguments.getString("email"));
            arguments.putString("imonbiliaria", arguments.getString("imonbiliaria"));
            arguments.putString("telefono", arguments.getString("telefono"));
            arguments.putDouble("latitud", arguments.getDouble("latitud"));
            arguments.putDouble("longitud", arguments.getDouble("longitud"));

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.containerdes, FragmentContenidoDescripcion.newInstance(arguments))
                    .commit();
        }
    }

}
