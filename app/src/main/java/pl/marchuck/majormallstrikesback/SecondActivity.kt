package pl.marchuck.majormallstrikesback

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pl.marchuck.majormallstrikesback.model.WeatherResponse
import pl.marchuck.majormallstrikesback.rest.Zipper
import java.util.*

class SecondActivity : AppCompatActivity() {
    val TAG = "SecondActivity";//::class.simpleName;
    var array = arrayOf(GoogleMap.MAP_TYPE_SATELLITE, GoogleMap.MAP_TYPE_TERRAIN, GoogleMap.MAP_TYPE_NONE,
            GoogleMap.MAP_TYPE_NORMAL, GoogleMap.MAP_TYPE_HYBRID);
    var x = 1;


    var drawerConnector = object : DrawerConnector {
        override fun onWeatherReceived(response: WeatherResponse) {
            //no-op
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        initDrawer(toolbar);

        val fab = findViewById(R.id.fab) as FloatingActionButton

        fab.setOnClickListener { view ->

            val mapFragment = SupportMapFragment.newInstance()
            SecondActivity@this.supportFragmentManager.beginTransaction()
                    .replace(R.id.map_content, mapFragment).commitAllowingStateLoss()

            Zipper.merger(SecondActivity@this, mapFragment,
                    object : Zipper.Zippo {
                        override fun onResponse(response: WeatherResponse, googleMap: GoogleMap) {

                            val latLng = LatLng(response.component1().lat,
                                    response.component1().lon);
                            //all maps stuff should be run on the main thread
                            Handler(Looper.getMainLooper()).post({

                                val markerOpts = MarkerOptions()
                                        .title(response.name)
                                        .snippet("See weather")
                                        .position(latLng);

                                googleMap.addMarker(markerOpts)
                                googleMap.setOnMarkerClickListener {
                                    marker ->
                                    drawerConnector.onWeatherReceived(response)
                                    false
                                }

                                googleMap.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(CameraPosition(latLng, 16f, 60f,
                                                        Random().nextInt(360).toFloat())));
                                googleMap.setOnMapLongClickListener {

                                    googleMap.mapType = nextMapType(++x);
                                }
                            })
                        }
                    })
        }
    }

    fun initDrawer(toolbar: Toolbar): Unit {
        Log.d(TAG, "initDrawer() called with: " + "");

        val drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(SecondActivity@this, drawerLayout, toolbar, android.R.string.ok,
                android.R.string.no);

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        toggle.isDrawerIndicatorEnabled = true;
        drawerLayout.closeDrawer(Gravity.RIGHT);

        drawerConnector = object : DrawerConnector {
            override fun onWeatherReceived(response: WeatherResponse) {
                drawerLayout.openDrawer(Gravity.RIGHT)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.drawer_layout_content, WeatherFragment.newInstance(response))
                        .commit();

            }
        }

    }

    fun nextMapType(x: Int): Int {
        return array[x % array.size];
    }

}

