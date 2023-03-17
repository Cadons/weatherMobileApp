package ch.supsi.dti.isin.meteoapp.http;

import org.json.JSONObject;

import ch.supsi.dti.isin.meteoapp.model.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface OpenWeatherAPIService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("data/2.5/weather?q=\"+city+\"&appid=\"+api_key+\"")
    Call<JSONObject> getWeatherByCity(@Query("city") String city, @Query("appid") String api_key);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("data/2.5/weather?lat=\"+lat+\"&lon=\"+lon+\"&appid=\"+api_key+\"")
    Call<JSONObject> getWeatherByCoordinates(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String api_key);

}
