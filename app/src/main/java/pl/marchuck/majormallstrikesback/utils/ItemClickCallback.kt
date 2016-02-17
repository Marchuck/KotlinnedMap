package pl.marchuck.majormallstrikesback.utils

import pl.marchuck.majormallstrikesback.model.poi.Prediction

/**
 * Created by Łukasz Marczak

 * @since 17.02.16
 */
interface ItemClickCallback {
    fun onClick(prediction: Prediction)
}
