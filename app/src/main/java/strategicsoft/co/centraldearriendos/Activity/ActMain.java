package strategicsoft.co.centraldearriendos.Activity;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import strategicsoft.co.centraldearriendos.ActMapas;
import strategicsoft.co.centraldearriendos.Adapter.DrawerItemAdapter;
import strategicsoft.co.centraldearriendos.Entity.DrawerItem;
import strategicsoft.co.centraldearriendos.Entity.DrawerMenu;
import strategicsoft.co.centraldearriendos.Fragment.FragmentBuscarInmueble;
import strategicsoft.co.centraldearriendos.Fragment.FragmentInmuele;
import strategicsoft.co.centraldearriendos.R;

public class ActMain extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final RecyclerView drawerOptions = (RecyclerView) findViewById(R.id.drawer_options);
        setSupportActionBar(toolbar);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerShadow(R.mipmap.drawer_shadow, Gravity.START);
        drawerLayout.setStatusBarBackground(R.color.color_principal);
        drawerLayout.setDrawerListener(drawerToggle);

        //getSupportActionBar().setHomeButtonEnabled(true);

        List<DrawerItem> drawerItems = new java.util.ArrayList<>(Arrays.asList(
                new DrawerItem(DrawerItem.Type.HEADER),
                new DrawerMenu().setIconRes(R.mipmap.ic_action_home).setText(getString(R.string.drawer_inmueble, 1)),
                new DrawerItem(DrawerItem.Type.DIVIDER),
                new DrawerMenu().setIconRes(R.mipmap.ic_action_search).setText(getString(R.string.drawer_buscar, 3)),
                new DrawerItem(DrawerItem.Type.DIVIDER),
                new DrawerMenu().setIconRes(R.mipmap.ic_action_globe).setText(getString(R.string.drawer_mapas, 5)),
                new DrawerItem(DrawerItem.Type.DIVIDER),
                new DrawerMenu().setIconRes(R.mipmap.ic_action_exit).setText(getString(R.string.drawer_salir, 7)),
                new DrawerItem(DrawerItem.Type.DIVIDER)));

        onDrawerMenuSelected(1);
        drawerOptions.setLayoutManager(new LinearLayoutManager(this));
        DrawerItemAdapter adapter = new DrawerItemAdapter(drawerItems);
        adapter.setOnItemClickListener(new DrawerItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                onDrawerMenuSelected(position);
            }
        });

        drawerOptions.setAdapter(adapter);
        drawerOptions.setHasFixedSize(true);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void onDrawerMenuSelected(int position) {

        FragmentManager fragmentManager = getFragmentManager();
        Bundle arguments = new Bundle();

        switch (position){
            case 1:
                //Inmuebles
                arguments.putString("operador", "list_completa");
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, FragmentInmuele.newInstance(arguments))
                        .commit();
                break;
            case 3:
                //Buscar
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, FragmentBuscarInmueble.newInstance(position + 1))
                        .commit();
                break;
            case 5:
                //Mapas

                Intent intent = new Intent(this, ActMapas.class);
                startActivity(intent);

                break;
            case 7:
                //Salir
                finishAffinity();
                break;
            default:

        }
        drawerLayout.closeDrawers();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


}
