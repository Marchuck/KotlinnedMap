package pl.marchuck.majormallstrikesback.model.weather

import java.io.Serializable

/**
 * Created by Łukasz Marczak
 * @since 11.02.16
 */
data class Weather(val id: Int,
                   val main: String,
                   val description: String,
                   val icon: String) : Serializable
