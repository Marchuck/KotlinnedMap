package pl.marchuck.majormallstrikesback.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.marchuck.majormallstrikesback.R

import pl.marchuck.majormallstrikesback.model.weather.WeatherResponse
import pl.marchuck.majormallstrikesback.utils.KotConverter
import pl.marchuck.majormallstrikesback.utils.WeatherAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {

    var weatherResponse: WeatherResponse? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherResponse = arguments?.getSerializable("WEATHER_RESPONSE") as WeatherResponse;

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view?.findViewById(R.id.recyclerView) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(activity);


        val dataSet = arrayOf(
                weatherResponse?.name,
                weatherResponse?.coord.toString(),
                weatherResponse?.weather?.get(0)?.description,
                "pressure: " + weatherResponse?.main?.pressure?.toString(),
                "humidity: " + weatherResponse?.main?.humidity,
                "Sea level: " + weatherResponse?.main?.sea_level.toString(),
                KotConverter.dateFromTimestamp(weatherResponse?.clouds?.all?.toLong()),
                "Sunrise: " + KotConverter.dateFromTimestamp(weatherResponse?.sys?.sunrise),
                "Sunset: " + KotConverter.dateFromTimestamp(weatherResponse?.sys?.sunset),
                weatherResponse?.wind.toString(),
                "Current temperature: " + KotConverter.kelvin2Celcius(weatherResponse?.main?.temp),
                "Min temperature: " + KotConverter.kelvin2Celcius(weatherResponse?.main?.temp_min),
                "Max temperature: " + KotConverter.kelvin2Celcius(weatherResponse?.main?.temp_max));

        recyclerView.adapter = WeatherAdapter(dataSet)
    }

    companion object {

        fun newInstance(): WeatherFragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(response: WeatherResponse): WeatherFragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            args.putSerializable("WEATHER_RESPONSE", response);
            fragment.arguments = args
            return fragment
        }
    }

}
