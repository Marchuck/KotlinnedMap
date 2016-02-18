package pl.marchuck.majormallstrikesback

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pl.marchuck.majormallstrikesback.model.GooglePlace
import pl.marchuck.majormallstrikesback.model.poi.MatchedSubstring
import pl.marchuck.majormallstrikesback.model.poi.Prediction
import pl.marchuck.majormallstrikesback.model.poi.Term
import pl.marchuck.majormallstrikesback.model.weather.WeatherResponse
import pl.marchuck.majormallstrikesback.rest.GoogleObservable
import pl.marchuck.majormallstrikesback.rest.Zipper
import pl.marchuck.majormallstrikesback.utils.ItemClickCallback
import pl.marchuck.majormallstrikesback.utils.PoiNearbyAdapter
import pl.marchuck.majormallstrikesback.utils.RxSuggestionWrapper
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class PlacesActivity : AppCompatActivity() {
    val TAG = "SecondActivity";//::class.simpleName;
    var array = arrayOf(GoogleMap.MAP_TYPE_SATELLITE, GoogleMap.MAP_TYPE_TERRAIN, GoogleMap.MAP_TYPE_NONE,
            GoogleMap.MAP_TYPE_NORMAL, GoogleMap.MAP_TYPE_HYBRID);
    var x = 1;


    var drawerConnector = object : DrawerConnector {
        override fun onWeatherReceived(response: WeatherResponse) {
            //no-op
        }
    }

    fun initSearchView(latLng: LatLng, map: GoogleMap): Unit {
        //        val rel = findViewById(R.id.drawer_layout_content) as RelativeLayout
        val ctx = SecondActivity@this
        val searchView = findViewById(R.id.search_view) as EditText
        val recycler = findViewById(R.id.recycler_view) as RecyclerView
        //        val suggestion = com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

        recycler.layoutManager = LinearLayoutManager(SecondActivity@this)
        val poiNearbyAdapter = PoiNearbyAdapter(arrayOf(emptyPrediction(), emptyPrediction()),
                object : ItemClickCallback {
                    override fun onClick(prediction: Prediction) {
                        //make get
                        val rest = GoogleObservable.getPlaceRest()
                        val id = prediction.place_id

                        GoogleObservable.getPlaceFromId(rest, ctx, id)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    googlePlace ->
                                    drawNewMarker(googlePlace, map)
                                }, {
                                    throwable ->
                                    Log.e(TAG,"error occurred "+throwable.message );

                                }

                                );
                    }
                })
        recycler.adapter = poiNearbyAdapter
        Log.d(TAG, "initSearchView()");
        val rest = GoogleObservable.getAutocompleteRest()

        RxSuggestionWrapper().init(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .flatMap { string ->
                    GoogleObservable.getAutocomplete(rest, SecondActivity@this, string, latLng)
                }
                .map { poi -> poi.predictions }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    suggestions ->
                    Log.d(TAG, "___________________________")
                    for (sugg in suggestions) {
                        Log.d(TAG, "next suggestion: " + sugg.description);
                    }
                    runOnUiThread {
                        poiNearbyAdapter.refreshWith(suggestions)
                    }
                    Log.d(TAG, "___________________________")
                    Log.d("DONE", "Cos sie dzieje: " + suggestions.size)
                }, { throwable -> Log.e("ERROR", "Error occurred: " + throwable.message) })
    }

    private fun emptyPrediction(): Prediction {
        val prediction = Prediction("", "", arrayOf(MatchedSubstring(0, 0)), "", "",
                arrayOf(Term(0, "")), arrayOf(""))
        return prediction;
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


                                initSearchView(latLng, googleMap);
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

    fun drawNewMarker(googlePlace: GooglePlace, map: GoogleMap): Unit {
        runOnUiThread {
            val name = googlePlace?.result?.name ?: "";
            val snippet = googlePlace?.result?.formatted_address ?: "";
            val lat = googlePlace?.result?.geometry?.location?.lat ?: 50.0;
            val lng = googlePlace?.result?.geometry?.location?.lng ?: 19.0;

            val latLng = LatLng(lat, lng)
            map.addMarker(MarkerOptions()
                    .position(latLng)
                    .title(name)
                    .snippet(snippet))
            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(CameraPosition(latLng, 16f, 60f,
                            Random().nextInt(360).toFloat())));
            val drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
            drawerLayout.closeDrawer(Gravity.RIGHT)
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
        val fab = findViewById(R.id.fab) as FloatingActionButton
        drawerLayout.setDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {
                fab.visibility = View.GONE;
            }

            override fun onDrawerClosed(drawerView: View) {
                fab.visibility = View.VISIBLE;

            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })
        drawerConnector = object : DrawerConnector {
            override fun onWeatherReceived(response: WeatherResponse) {
                drawerLayout.openDrawer(Gravity.RIGHT)
                /*
            supportFragmentManager.beginTransaction()
                    .replace(R.id.drawer_layout_content, WeatherFragment.newInstance(response))
                    .commit();
*/
            }
        }

    }

    fun nextMapType(x: Int): Int {
        return array[x % array.size];
    }

}

