package id.co.imastudio.bmkgapp22W;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class AnalisisIklim extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.textviewtanggal)
    TextView textviewtanggal;
    @BindView(R.id.searchprovinsi)
    AutoCompleteTextView searchprovinsi;
    @BindView(R.id.tabsdetail)
    TabLayout tabsdetail;
    @BindView(R.id.pager)
    ViewPager pager;


    private IklimOperations operations ;
    private final static String TAG = "BroadcastService";
    LatLngBounds bound ;
    String lat,lon ;


    ArrayList<Iklim> data ;
    private GoogleMap mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analisis_iklim);
        ButterKnife.bind(this);



        operations = new IklimOperations(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapiklim);
        mapFragment.getMapAsync(this);

        startService(new Intent(this, BroadcastService.class));

            setUptab();

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void setUptab()  {







        final Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DATE,+10);

        Date satu = calender.getTime();

        DateFormat dateformat = new SimpleDateFormat("MMM yyyy");
        String dua = dateformat.format(satu);

        Calendar calender2 = Calendar.getInstance();
        calender2.add(Calendar.DATE,-10);

        Date satu2 = calender.getTime();

        DateFormat dateformat2 = new SimpleDateFormat("MMM yyyy");
        String dua2 = dateformat2.format(satu2);


 Calendar calender3 = Calendar.getInstance();
        calender3.add(Calendar.DATE,0);

        Date satu3 = calender3.getTime();

        DateFormat dateformat3 = new SimpleDateFormat("MMM yyyy");
        String dua3 = dateformat3.format(satu3);






        tabs.addTab(tabs.newTab().setText(dua2 + "- I"));
        tabs.addTab(tabs.newTab().setText(dua3 +"- II"));
        tabs.addTab(tabs.newTab().setText(dua +"- III"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap ;

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
        ArrayAdapterr adapter = new ArrayAdapterr(this,R.layout.item_auto,data);
        searchprovinsi.setThreshold(1);

        searchprovinsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });
        searchprovinsi.setAdapter(adapter);



        operations.close();
        Log.d("jumlah :" , String.valueOf(data.get(0)));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for(int i = 0 ; i < data.size(); i ++){
            String name = data.get(i).getId();
            lat = data.get(i).getLat();
            lon = data.get(i).getLng();
            builder.include(new LatLng(Double.parseDouble(lat),Double.parseDouble(lon)));
            Double hdh = Double.parseDouble(data.get(i).getHdt());
            LatLng koor = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
            //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(koor,13));






            if (hdh < 20){


                mMap.addMarker(new MarkerOptions().position(koor).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.hijau)));



            }
            else if (hdh > 21 && hdh < 50){
                mMap.addMarker(new MarkerOptions().position(koor).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.kuning)));

            }
            else if (hdh > 50 && hdh < 100){
                mMap.addMarker(new MarkerOptions().position(koor).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.orange)));

            } else if (hdh > 100 && hdh < 150){
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
        }

        operations.close();

        getDatabase();




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

}
