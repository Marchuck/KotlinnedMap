package pl.marchuck.majormallstrikesback.model.weather

import java.io.Serializable


/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
data class Sys(val message: Double,
               val country: String,
               val sunrise: Long,
               val sunset: Long) : Serializable
