package pl.marchuck.majormallstrikesback.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Łukasz Marczak
 * @since 17.02.16
 */
data class Geometry(val location: Loc?,
                  val viewport: GViewport?);