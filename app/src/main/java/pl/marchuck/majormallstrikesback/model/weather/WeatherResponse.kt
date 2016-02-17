package pl.marchuck.majormallstrikesback.model.weather

import java.io.Serializable

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
data class WeatherResponse(val coord: Coord,
                           val weather: List<Weather>,
                           val base: String,
                           val main: Main,
                           val wind: Wind,
                           val clouds: Clouds,
                           val dt: Int,
                           val sys: Sys,
                           val id: Int,
                           val name: String,
                           val cod: Int) : Serializable;

