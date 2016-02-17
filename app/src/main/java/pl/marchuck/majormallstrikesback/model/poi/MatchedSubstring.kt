package pl.marchuck.majormallstrikesback.model.poi

import java.io.Serializable

/**
 * Created by Łukasz Marczak
 * @since 14.02.16
 */
data class MatchedSubstring(val length: Int,
                            val offset: Int) : Serializable