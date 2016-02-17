package pl.marchuck.majormallstrikesback.model.weather

import java.io.Serializable

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
data class Wind(val speed: Double,
                val deg: Double) : Serializable