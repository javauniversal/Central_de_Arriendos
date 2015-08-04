package strategicsoft.co.centraldearriendos.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.text.DecimalFormat;

import strategicsoft.co.centraldearriendos.Activity.DescripcionArriendosActivitys;
import strategicsoft.co.centraldearriendos.Entity.PublicationList;
import strategicsoft.co.centraldearriendos.R;
import strategicsoft.co.centraldearriendos.views.ProgressBarCircularIndetermininate;

public class AdapterPublication extends BucketListAdapterProduct {

    private Activity mActivity;
    private PublicationList elements;
    private DisplayImageOptions options1;
    private ImageLoader imageLoader1;

    public AdapterPublication(Activity ctx, PublicationList elements) {
        super(ctx, elements);
        this.mActivity=ctx;
        this.elements = elements;

        //Setup the ImageLoader, we'll use this to display our images
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mActivity).build();
        imageLoader1 = ImageLoader.getInstance();
        imageLoader1.init(config);
        //Setup options for ImageLoader so it will handle caching for us.
        options1 = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .build();

    }

    @Override
    protected View getBucketElement(final int position) {
        final ViewHolder2 holder;
        View bucketElement;
        LayoutInflater inflater = mActivity.getLayoutInflater();
        bucketElement = inflater.inflate(R.layout.play_giditem, null);
        holder = new ViewHolder2(bucketElement);
        bucketElement.setTag(holder);

        ImageLoadingListener listener = new ImageLoadingListener(){
            @Override
            public void onLoadingStarted(String arg0, View arg1) {
                // TODO Auto-generated method stub
                holder.indicator1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {
                // TODO Auto-generated method stub
                holder.indicator1.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                holder.indicator1.setVisibility(View.GONE);
            }
            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                // TODO Auto-generated method stub
                holder.indicator1.setVisibility(View.GONE);
            }
        };

        imageLoader1.displayImage(elements.get(position).getUrlImagen(), holder.img, options1, listener);

        DecimalFormat format = new DecimalFormat("$#,###.##");

        holder.ciudad.setText(elements.get(position).getCiudad());
        holder.barrio.setText(elements.get(position).getBarrio());
        holder.precie.setText(format.format(elements.get(position).getPrecio()));
        bucketElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showMessage("position :" + position);
                Intent intent = new Intent(mActivity, DescripcionArriendosActivitys.class);
                intent.putExtra("ciudad", elements.get(position).getCiudad());
                intent.putExtra("barrio", elements.get(position).getBarrio());
                intent.putExtra("tipologia", elements.get(position).getTipoNegocio());
                intent.putExtra("propiedad", elements.get(position).getTipologia());
                intent.putExtra("precio", elements.get(position).getPrecio());
                intent.putExtra("habitaciones", elements.get(position).getAlcobas());
                intent.putExtra("banos", elements.get(position).getBanos());
                intent.putExtra("area", elements.get(position).getArea());
                intent.putExtra("estrato", elements.get(position).getEstrato());
                intent.putExtra("admin", elements.get(position).getCosteAdministracion());
                intent.putExtra("contacto", elements.get(position).getUsuario());
                intent.putExtra("email", elements.get(position).getEmail());
                intent.putExtra("imonbiliaria", elements.get(position).getInmobiliaria());
                intent.putExtra("telefono", elements.get(position).getTelefono());
                intent.putExtra("latitud", elements.get(position).getLatitud());
                intent.putExtra("longitud", elements.get(position).getLongitud());
                mActivity.startActivity(intent);
            }
        });

        return bucketElement;
    }

    class ViewHolder2 {
        public TextView ciudad = null;
        public TextView barrio = null;
        public TextView precie = null;
        public ImageView img = null;
        private ProgressBarCircularIndetermininate indicator1;

        ViewHolder2(View row) {
            ciudad = (TextView) row.findViewById(R.id.projectName);
            img = (ImageView) row.findViewById(R.id.listicon);
            barrio = (TextView) row.findViewById(R.id.Name);
            precie = (TextView) row.findViewById(R.id.companyName);
            indicator1 = (ProgressBarCircularIndetermininate) row.findViewById(R.id.progressBarCircularImagen);
        }
        void populateFrom(String s) {
            ciudad.setText(s);
        }
    }

}
