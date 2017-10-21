package id.co.imastudio.bmkgapp22W.Http;

import id.co.imastudio.bmkgapp22W.Response.ResponseHdh;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nandoseptianhusni on 10/21/17.
 */

public interface ApiService {


    @GET("sample.php")
    Call<ResponseHdh> request_hdh();


}
