package pl.marchuck.majormallstrikesback.model.poi

import java.io.Serializable

/**
 * Created by Łukasz Marczak
 * @since 14.02.16
 */
data class Prediction(
        val description: String,
        val id: String,
        val matched_substrings: Array<MatchedSubstring>,
        val place_id: String,
        val reference: String,
        val terms: Array<Term>,
        val types: Array<String>) : Serializable
