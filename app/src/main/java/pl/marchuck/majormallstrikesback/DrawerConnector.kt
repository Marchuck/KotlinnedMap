package pl.marchuck.majormallstrikesback

import pl.marchuck.majormallstrikesback.model.WeatherResponse

/**
 * Created by Łukasz Marczak
 * @since 14.02.16
 */
interface DrawerConnector {
    fun onWeatherReceived(response: WeatherResponse): Unit;
}