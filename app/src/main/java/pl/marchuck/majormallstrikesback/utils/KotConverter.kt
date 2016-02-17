package pl.marchuck.majormallstrikesback.utils

import android.gesture.Prediction
import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import java.sql.Timestamp
import java.util.*

/**
 * Created by Łukasz Marczak
 * @since 14.02.16
 */
object KotConverter {

    fun kelvin2Celcius(value: Double?): String {
        if (value === null) {
            return "";
        } else
            return "" + ((value - 273.0).toInt()) + " st. C";
    }

    fun dateFromTimestamp(value: Long?): String {
        if (value === null) return "";
        val stamp = Timestamp(value);
        val date = Date(stamp.time);

        return "" + date.hours + ":" + date.minutes;
    }

    internal class PoiSuggestion : SearchSuggestion{

        override fun getBody(): String? {
            throw UnsupportedOperationException()
        }

        override fun getCreator(): Parcelable.Creator<*>? {
            throw UnsupportedOperationException()
        }

        override fun writeToParcel(p0: Parcel?, p1: Int) {
            throw UnsupportedOperationException()
        }

        override fun describeContents(): Int {
            throw UnsupportedOperationException()
        }

    }
}