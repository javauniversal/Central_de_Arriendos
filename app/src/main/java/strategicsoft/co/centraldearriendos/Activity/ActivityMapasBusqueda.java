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

public class ActivityMapasBusqueda extends AppCompatActivity {


    Bundle arguments;
    private ImageView drawer_indicator;
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
            arguments.putString("operador", arguments.getString("operador"));
            arguments.putString("id_publicacion", arguments.getString("id_publicacion"));
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.containerdesser, FragmentDestacados.newInstance(arguments))
                    .commit();
        }
    }
}
