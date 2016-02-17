package pl.marchuck.majormallstrikesback.rest

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import pl.marchuck.majormallstrikesback.R
import pl.marchuck.majormallstrikesback.model.GooglePlace
import pl.marchuck.majormallstrikesback.model.poi.Poi
import rx.Observable

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
object GoogleObservable {

    fun getMap(mapFragment: SupportMapFragment?): rx.Observable<GoogleMap> {
        return Observable.create { subscriber ->
            if (mapFragment == null) {
                subscriber.onError(Throwable("Null mapFragment"))
            } else
                mapFragment.getMapAsync(OnMapReadyCallback { googleMap ->
                    subscriber.onNext(googleMap)
                    subscriber.onCompleted()
                })
        }
    }

    fun getAutocompleteRest(): GooglePlacesAPI {
        return GenericAdapter<Poi>(GooglePlacesAPI.endpoint, Poi::class.java)
                .adapter.create(GooglePlacesAPI::class.java)
    }

    fun getPlaceRest(): GooglePlacesAPI {
        return GenericAdapter<GooglePlace>(GooglePlacesAPI.endpoint, GooglePlace::class.java)
                .adapter.create(GooglePlacesAPI::class.java)
    }

    fun getAutocomplete(rest: GooglePlacesAPI, ctx: Context, query: String, latLng: LatLng): rx.Observable<Poi> {
        val apiKey = ctx.resources.getString(R.string.google_api_key)
        val position = "" + latLng.latitude + "," + latLng.longitude;
        return rest.getAutocomplete(query, apiKey, 3, position, 10000);
    }

    fun getPlaceFromId(rest: GooglePlacesAPI, ctx: Context, place_id: String): rx.Observable<GooglePlace> {
        val apiKey = ctx.resources.getString(R.string.google_api_key)
        return rest.getGooglePlace(place_id, apiKey);
    }

}
