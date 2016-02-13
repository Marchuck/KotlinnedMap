package pl.marchuck.majormallstrikesback.rest

import android.util.Log

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException

import retrofit.RestAdapter
import retrofit.converter.GsonConverter

/**
 * Created by Łukasz Marczak

 * @since 11.02.16
 */
class GenericAdapter<T>(endpoint: String, templateClass: Class<T>) {
    val adapter: RestAdapter

    init {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(templateClass, object : Deserializer<T>() {
            override fun setDestinationClass(): Class<T> {
                return templateClass
            }
        })
        val gson = builder.create()
        val converter = GsonConverter(gson)
        adapter = RestAdapter.Builder().setEndpoint(endpoint).setConverter(converter).build()
    }


    private abstract class Deserializer<T> : JsonDeserializer<T> {

        abstract fun setDestinationClass(): Class<T>

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: java.lang.reflect.Type,
                                 context: JsonDeserializationContext): T {

            return Gson().fromJson(json, setDestinationClass())
        }
    }
}
