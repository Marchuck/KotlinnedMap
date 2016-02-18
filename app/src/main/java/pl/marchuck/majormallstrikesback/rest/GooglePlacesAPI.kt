package pl.marchuck.majormallstrikesback.rest

import pl.marchuck.majormallstrikesback.model.GooglePlace
import pl.marchuck.majormallstrikesback.model.poi.Poi
import retrofit.http.GET
import retrofit.http.Query

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
interface GooglePlacesAPI {

    @GET("/autocomplete/json")
    fun getAutocomplete(@Query("input") query: String,
                       @Query("key") apiKey: String,
                       @Query("offset") offset: Int,
                       @Query("location") location: String,
                       @Query("radius") radius: Int
                        ): rx.Observable<Poi>
    @GET("/details/json")
    fun getGooglePlace(@Query("placeid") place_id: String,
                       @Query("key") apiKey: String
                        ): rx.Observable<GooglePlace>

    companion object {
        val endpoint = "https://maps.googleapis.com/maps/api/place"
    }



}
