package pl.marchuck.majormallstrikesback

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pl.marchuck.majormallstrikesback.model.WeatherResponse
import pl.marchuck.majormallstrikesback.rest.Zipper

class SecondActivity : AppCompatActivity() {

    var array = arrayOf(GoogleMap.MAP_TYPE_SATELLITE, GoogleMap.MAP_TYPE_TERRAIN, GoogleMap.MAP_TYPE_NONE,
            GoogleMap.MAP_TYPE_NORMAL, GoogleMap.MAP_TYPE_HYBRID);
    var x = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second2)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->

            val mapFragment = SupportMapFragment.newInstance()
            SecondActivity@this.supportFragmentManager.beginTransaction()
                    .replace(R.id.content2, mapFragment).commitAllowingStateLoss()

            Zipper.merger(SecondActivity@this, mapFragment,
                    object : Zipper.Zippo {
                        override fun onResponse(response: WeatherResponse, googleMap: GoogleMap) {

                            val latLng = LatLng(response.component1().lat,
                                    response.component1().lon);
                            //all maps stuff should be run on the main thread
                            Handler(Looper.getMainLooper()).post({
                                val markerOpts = MarkerOptions()
                                        .title(response.name).snippet(response.wind.toString())
                                        .position(latLng);
                                googleMap.addMarker(markerOpts)

                                googleMap.setOnMapLongClickListener {

                                    googleMap.mapType = nextMapType(++x);
                                }
                            })
                        }
                    })
        }
    }

    fun nextMapType(x: Int): Int {
        return array[x % array.size];
    }

}
