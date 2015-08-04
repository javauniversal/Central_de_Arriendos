package strategicsoft.co.centraldearriendos.Fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import strategicsoft.co.centraldearriendos.Adapter.AdapterPublication;
import strategicsoft.co.centraldearriendos.Entity.Publication;
import strategicsoft.co.centraldearriendos.Entity.PublicationList;
import strategicsoft.co.centraldearriendos.R;

public class FragmentInmuele extends BaseVolleyFragment {

    private ListView multiColumnList;
    private ImageView mErrorView;
    private static Activity activity;
    private String argumentos;
    private PublicationList publicationList;


    public static FragmentInmuele newInstance(Bundle bundle) {
        FragmentInmuele fragment = new FragmentInmuele();
        fragment.setArguments(bundle);
        return fragment;
    }

    public FragmentInmuele() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            argumentos = getArguments().getString("operador");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inmuele, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        multiColumnList = (ListView) getActivity().findViewById(R.id.card_list);
        mErrorView = (ImageView) getActivity().findViewById(R.id.error_data);
        activity = getActivity();

        setupGrid();

    }

    private void setupGrid() {
        String url = "http://www.appgestor.com/ServicioWebArriendos/service_database.php";
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        if (!parseJSON(response)) {
                            Toast.makeText(getActivity(), "No se encontraron datos", Toast.LENGTH_LONG).show();
                            mErrorView.setVisibility(View.VISIBLE);
                        }else{
                            AdapterPublication adapter = new AdapterPublication(activity, publicationList);
                            adapter.enableAutoMeasure(150);
                            multiColumnList.setAdapter(adapter);
                            mErrorView.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        onConnectionFailed(error.toString());
                        mErrorView.setVisibility(View.GONE);
                    }
                }
        ) {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            protected Map<String, String> getParams(){
                Map<String, String>  params = new HashMap<>();
                if(argumentos.equals("list_completa")){
                    params.put("operador", "ofertasList");
                }else if(argumentos.equals("list_busqueda")){
                    params.put("operador", "list_busqueda");
                    params.put("ciudad", getArguments().getString("ciudad"));
                    params.put("municipio", getArguments().getString("municipio"));
                    params.put("barrio", getArguments().getString("barrio"));
                    params.put("tipo_negocio", getArguments().getString("tipo_negocio"));
                    params.put("tipo_propiedad", getArguments().getString("tipo_propiedad"));
                    params.put("estrato", getArguments().getString("estrato"));
                    params.put("precio_ini", getArguments().getString("precio_ini"));
                    params.put("precio_fin", getArguments().getString("precio_fin"));
                }else{
                    params.put("operador", "list_busqueda_mapa");
                    params.put("id_publicacion", getArguments().getString("id_publicacion"));
                }
                return params;
            }
        };
        addToQueue(jsonRequest);

    }

    private boolean parseJSON(String json) {
        try {
            Gson gson = new Gson();
            publicationList = gson.fromJson(json, PublicationList.class);
        }catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
