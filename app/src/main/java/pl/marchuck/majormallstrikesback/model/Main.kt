package pl.marchuck.majormallstrikesback.model

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
data class Main(val temp:Double,val pressure: Int,val humidity: Int,val tempMin:Double,
                val tempMax: Double,val seaLevel: Double, val grndLevel:Int)
