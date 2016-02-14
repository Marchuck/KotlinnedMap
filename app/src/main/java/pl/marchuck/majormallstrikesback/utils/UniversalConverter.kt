package pl.marchuck.majormallstrikesback.utils

import java.sql.Timestamp
import java.util.*

/**
 * Created by Łukasz Marczak
 * @since 14.02.16
 */
object UniversalConverter {

    fun kelvin2Celcius(value: Double?): String {
        if (value === null) {
            return "";
        } else
            return (value - 273.0).toString();
    }

    fun dateFromTimestamp(value: Long?): String {
        if (value === null) return "";
        val stamp = Timestamp(value);
        val date = Date(stamp.time);
        return date.toString();
    }
}