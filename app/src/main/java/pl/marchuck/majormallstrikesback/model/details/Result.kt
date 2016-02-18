package pl.marchuck.majormallstrikesback.model.details

/**
 * Created by Łukasz Marczak
 * @since 17.02.16
 */
data class Result(val address_components: Array<AddrComponent>?,
                  val adr_address: String?,
                  val formatted_address: String?,
                  val geometry: Geometry?,
                  val icon: String?,
                  val id: String?,
                  val name: String?,
                  val place_id: String?,
                  val reference: String?,
                  val scope: String?,
                  val types: Array<String>?,
                  val url: String?,
                  val vicinity: String?);