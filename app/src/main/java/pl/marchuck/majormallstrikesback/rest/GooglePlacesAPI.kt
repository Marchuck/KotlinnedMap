package pl.marchuck.majormallstrikesback.rest

import pl.marchuck.majormallstrikesback.model.GooglePlace
import pl.marchuck.majormallstrikesback.model.poi.Poi
import pl.marchuck.majormallstrikesback.model.weather.WeatherResponse
import retrofit.http.GET
import retrofit.http.Query

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
interface GooglePlacesAPI {

    @GET("/json")
    fun getAutocomplete(@Query("input") query: String,
                       @Query("key") apiKey: String,
                       @Query("offset") offset: Int,
                       @Query("location") location: String,
                       @Query("radius") radius: Int
                        ): rx.Observable<Poi>
    @GET("/json")
    fun getGooglePlace(@Query("placeid") place_id: String,
                       @Query("key") apiKey: String
                        ): rx.Observable<GooglePlace>

    companion object {
        val endpoint = "https://maps.googleapis.com/maps/api/place/autocomplete"
    }


}
