package pl.marchuck.majormallstrikesback.rest

import android.content.Context

import pl.marchuck.majormallstrikesback.R
import pl.marchuck.majormallstrikesback.model.WeatherResponse
import rx.Observable

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
object WeatherObservable {

    fun getWeather(ctx: Context): Observable<WeatherResponse> {
        val apiKey = ctx.resources.getString(R.string.weather_api_key)
        return GenericAdapter(WeatherClient.endpoint, WeatherResponse::class.java)
                .adapter.create(WeatherClient::class.java).getCityWeather("Krakow,pl", apiKey)
    }
}
