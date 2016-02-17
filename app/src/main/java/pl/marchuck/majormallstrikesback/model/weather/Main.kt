package pl.marchuck.majormallstrikesback.model.weather

import java.io.Serializable

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
data class Main(val temp:Double,
                val pressure: Int,
                val humidity: Int,
                val temp_min:Double,
                val temp_max: Double,
                val sea_level: Double,
                val grnd_level:Int) : Serializable
