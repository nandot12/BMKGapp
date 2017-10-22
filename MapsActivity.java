package id.co.imastudio.bmkgapp22W;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import id.co.imastudio.bmkgapp22W.Database.IklimOperations;
import id.co.imastudio.bmkgapp22W.Http.ApiService;
import id.co.imastudio.bmkgapp22W.Http.ConfigRetrofit;
import id.co.imastudio.bmkgapp22W.Model.Iklim;
import id.co.imastudio.bmkgapp22W.Response.HariTanpaHujanItem;
import id.co.imastudio.bmkgapp22W.Response.PosHujanItem;
import id.co.imastudio.bmkgapp22W.Response.ResponseHdh;
import id.co.imastudio.bmkgapp22W.Service.BroadcastService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private IklimOperations operations ;
    private final static String TAG = "BroadcastService";
    LatLngBounds bound ;
    String lat,lon ;


    List<Iklim> data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        operations = new IklimOperations(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.




        startService(new Intent(this, BroadcastService.class));
       // Log.i(TAG, "Started service");


    }
    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.i(TAG, "Registered broacast receiver");
    }

    @Override
    public void onPause() {
        super.onPause();
        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
      //  unregisterReceiver(br);
        Log.i(TAG, "Unregistered broacast receiver");
    }

    @Override
    public void onStop() {
        try {
            registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
          //  unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }
    @Override
    public void onDestroy() {
       // stopService(new Intent(this, BroadcastService.class));
        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.i(TAG, "Stopped service");
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            Log.i(TAG, "Count second: " +  millisUntilFinished / 1000);

            if(millisUntilFinished/1000 == 1){

              //  Toast.makeText(getApplicationContext(),"nando",Toast.LENGTH_LONG).show();

                operations.open();
                operations.deleteAll();

                operations.close();

            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng indonesia = new LatLng(-2.2756283,99.4359756);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia,14));

        mMap.getUiSettings().setZoomControlsEnabled(true);

                operations.open();

        if(operations.getAllIklim().size() == 0){
            operations.close();
            getData();

         //   Toast.makeText(getApplicationContext(),"satu",Toast.LENGTH_LONG).show();

        }
        else{
            getDatabase();
          //  Toast.makeText(getApplicationContext(),"dua",Toast.LENGTH_LONG).show();
        }







    }

    private void getDatabase() {

       operations.open();



         data = operations.getAllIklim();



        operations.close();
        Log.d("jumlah :" , String.valueOf(data.get(0)));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for(int i = 0 ; i < data.size(); i ++){
            String name = data.get(i).getId();
            lat = data.get(i).getLat();
            lon = data.get(i).getLng();
            builder.include(new LatLng(Double.parseDouble(lat),Double.parseDouble(lon)));
            int hdh = Integer.parseInt(data.get(i).getHdh());
            LatLng koor = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
            //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(koor,13));






            if (hdh >1 && hdh < 5){


                mMap.addMarker(new MarkerOptions().position(koor).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.hijau)));



            }
            else if (hdh > 5 && hdh < 10){
                mMap.addMarker(new MarkerOptions().position(koor).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.kuning)));

            }
            else if (hdh > 10 && hdh < 20){
                mMap.addMarker(new MarkerOptions().position(koor).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.orange)));

            } else if (hdh > 20 && hdh < 30){
                mMap.addMarker(new MarkerOptions().position(koor).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.merah)));

            }


        }





        bound = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bound,12));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bound,12));

    }

    private void getData() {

        ApiService api = ConfigRetrofit.getIntance() ;
        Call<ResponseHdh> call = api.request_hdh();
        call.enqueue(new Callback<ResponseHdh>() {
            @Override
            public void onResponse(Call<ResponseHdh> call, Response<ResponseHdh> response) {
                //check response server
                Log.d("response",response.message());
                if(response.isSuccessful()){
                    List<HariTanpaHujanItem> data = response.body().getHariTanpaHujan();
                    List<PosHujanItem> poshujan = data.get(0).getPosHujan();
                    setSqlite(poshujan);
                    
                }
                else{
                    Log.d("error response",response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<ResponseHdh> call, Throwable t) {
                
                Log.d("error server",t.getMessage());

            }
        });

    }

    private void setSqlite(List<PosHujanItem> poshujan) {



       operations.open();

        for (int i = 0 ; i < poshujan.size(); i++){
            Iklim iklim = new Iklim();
            iklim.setId(poshujan.get(i).getId());
            iklim.setNama(poshujan.get(i).getNama());
            iklim.setHdh(poshujan.get(i).getHdh());
            iklim.setHdt(poshujan.get(i).getHdt());
            iklim.setIdProv(poshujan.get(i).getIdProv());
            iklim.setLat(poshujan.get(i).getLat());
            iklim.setLng(poshujan.get(i).getLng());
            iklim.setKriteria(poshujan.get(i).getKriteria());
            iklim.setProv(poshujan.get(i).getProv());
            operations.addIklim(iklim);

            String name = poshujan.get(i).getNama();
        }

       operations.close();

        getDatabase();




    }


}
