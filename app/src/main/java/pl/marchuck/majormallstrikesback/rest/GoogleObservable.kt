package pl.marchuck.majormallstrikesback.rest

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import rx.Observable
import rx.Subscriber

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
}
