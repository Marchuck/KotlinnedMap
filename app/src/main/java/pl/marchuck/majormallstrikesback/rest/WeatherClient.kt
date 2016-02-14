package pl.marchuck.majormallstrikesback.rest

import pl.marchuck.majormallstrikesback.model.WeatherResponse
import retrofit.http.GET
import retrofit.http.Query

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
interface WeatherClient {

    @GET("/data/2.5/weather")
    fun getCityWeather(@Query("q") query: String, @Query("appid") apiKey: String): rx.Observable<WeatherResponse>

    companion object {
        val endpoint = "http://api.openweathermap.org"
    }


}
