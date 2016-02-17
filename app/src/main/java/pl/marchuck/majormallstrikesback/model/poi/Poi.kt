package pl.marchuck.majormallstrikesback.model.poi

import java.io.Serializable

/**
 * Created by Łukasz Marczak
 * @since 14.02.16
 */
data class Poi(val status: String,
               val predictions: Array<Prediction>) : Serializable
