package pl.marchuck.majormallstrikesback

import com.google.android.gms.maps.GoogleMap
import pl.marchuck.majormallstrikesback.model.weather.WeatherResponse

/**
 * Created by Łukasz Marczak
 * @since 14.02.16
 */
interface DrawerConnector {
    fun onWeatherReceived(response: WeatherResponse): Unit;
}