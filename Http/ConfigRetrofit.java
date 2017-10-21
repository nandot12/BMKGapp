package id.co.imastudio.bmkgapp22W.Http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nandoseptianhusni on 10/21/17.
 */

public class ConfigRetrofit {


    public static Retrofit getRetrofit(){
        return new Retrofit.Builder().baseUrl("http://192.168.20.17/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getIntance(){
        return getRetrofit().create(ApiService.class);
    }
}
