package strategicsoft.co.centraldearriendos;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import strategicsoft.co.centraldearriendos.Activity.ActivityMapasBusqueda;
import strategicsoft.co.centraldearriendos.views.Button;

public class ActMapas extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public static final String TAG = ActMapas.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    protected int idPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mapas);
        setUpMapIfNeeded();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String[] separated = marker.getTitle().split(":");
                separated[1] = separated[1].trim();
                //showMessage(separated[1]);

                Intent intent = new Intent(ActMapas.this, ActivityMapasBusqueda.class);
                intent.putExtra("operador", "list_mapa");
                intent.putExtra("id_publicacion", String.valueOf(separated[1]));
                startActivity(intent);
            }
        });

        setupGrid();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            //handleNewLocation(location);
            LatLng latLngt = new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngt, 7);
            mMap.animateCamera(cameraUpdate);
        }
    }



    private void setupGrid() {

        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        String url = "http://www.appgestor.com/ServicioWebArriendos/positionMaps.php";
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        // response
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //showMessage("Error Volly");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String>  params = new HashMap<>();
                params.put("operador", "posicionesMapas");
                return params;
            }
        };
        queue.add(jsonRequest);
    }

    private void parseJSON(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray mServicio = jsonObject.getJSONArray("publication");
            for (int i = 0; i < mServicio.length(); i++) {
                JSONObject pue = mServicio.getJSONObject(i);
                LatLng variable = new LatLng(pue.getDouble("latitud"), pue.getDouble("longitud"));
                idPublicacion = pue.getInt("id");
                String tipilogia = pue.getString("tipologia");
                double precio = pue.getDouble("precio");
                setMarker(variable,"Código: "+idPublicacion, precio);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //handleNewLocation(location);
    }


    //Metodo dende implementamos la vista del marke
    public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;
        private TextView mTitulo;
        private TextView mPrecio;
        private Button informacion;

        MyInfoWindowAdapter() {
            myContentsView = getLayoutInflater().inflate(
                    R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {

            mTitulo = ((TextView) myContentsView.findViewById(R.id.txtCiudad));
            mTitulo.setText(marker.getTitle());
            mPrecio = ((TextView) myContentsView.findViewById(R.id.txtPrecio));
            mPrecio.setText(marker.getSnippet());

            return myContentsView;
        }

    }


    private void setMarker(LatLng position, String titulo, Double info) {
        DecimalFormat format = new DecimalFormat("$#,###.##");
        mMap.addMarker(new MarkerOptions().position(position)
                .title(titulo)
                .snippet("Precio: " + format.format(info))
                .anchor(0.5f, 0.5f)
                .flat(true)
                .visible(true)
                .draggable(true)
                .flat(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bigcity)));
        // myMaker.showInfoWindow();
    }



}
