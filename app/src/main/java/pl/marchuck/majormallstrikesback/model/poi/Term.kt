package pl.marchuck.majormallstrikesback.model.poi

import android.gesture.Prediction
import java.io.Serializable

/**
 * Created by Łukasz Marczak
 * @since 14.02.16
 */
data class Term(val offset: Int,
                val value: String) : Serializable
