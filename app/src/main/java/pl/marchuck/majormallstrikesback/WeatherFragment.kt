package pl.marchuck.majormallstrikesback


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.marchuck.majormallstrikesback.model.Main

import pl.marchuck.majormallstrikesback.model.WeatherResponse
import pl.marchuck.majormallstrikesback.model.wrappers.MainSerializable
import pl.marchuck.majormallstrikesback.utils.UniversalConverter

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {

    var clouds: String? = null;
    var main: Main? = null;
    var weather: String? = null;
    var sunset: Long? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clouds = arguments?.getString("CLOUDS");
        weather = arguments?.getString("WEATHER");
        sunset = arguments?.getLong("SUNSET");
        val mains = arguments?.getSerializable("MAIN");
        if (mains is MainSerializable) {
            main = mains.main
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name0 = view?.findViewById(R.id.name0) as TextView
        val name1 = view?.findViewById(R.id.name1) as TextView
        val name2 = view?.findViewById(R.id.name2) as TextView
        val name3 = view?.findViewById(R.id.name3) as TextView
        val name4 = view?.findViewById(R.id.name4) as TextView
        name0.text = clouds;
        name1.text = weather;
        name2.text = UniversalConverter.kelvin2Celcius(main?.temp);
        name3.text = UniversalConverter.dateFromTimestamp(sunset);

        //        name1.text = weatherResponse?.base;
        //        name2.text = weatherResponse?.component1().toString();
        //        name3.text = weatherResponse?.component2()?.get(0).toString();
        //        name4.text = weatherResponse?.component6().toString();
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
            args.putString("CLOUDS", response.clouds.toString());
            args.putString("WEATHER", response.weather[0].description);
            args.putLong("SUNSET", response.sys.sunset);
            args.putSerializable("MAIN", MainSerializable(response.main));
            fragment.arguments = args
            return fragment
        }
    }

}
