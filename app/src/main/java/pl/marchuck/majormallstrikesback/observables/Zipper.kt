package pl.marchuck.majormallstrikesback.observables

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import pl.marchuck.majormallstrikesback.model.weather.WeatherResponse
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func2

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
object Zipper {

    interface Zippo {
        fun onResponse(response: WeatherResponse, googleMap: GoogleMap)
    }

    fun merger(ctx: Context, mapFragment: SupportMapFragment, callback: Zippo) {

        Observable.zip(WeatherObservable.getWeather(ctx),
                GoogleObservable.getMap(mapFragment),
                Func2<WeatherResponse,
                        GoogleMap, String> { response, googleMap ->
                    callback.onResponse(response, googleMap)

                    return@Func2 "" + response.coord.lon + "," + response.coord.lat
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d("", "done") }) { throwable ->
            Log.d("", "error " + throwable.message)
            Log.d("", "error " + throwable.cause?.message)

        }
    }
}
