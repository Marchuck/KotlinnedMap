package pl.marchuck.majormallstrikesback.utils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import pl.marchuck.majormallstrikesback.R
import pl.marchuck.majormallstrikesback.model.poi.Prediction

/**
 * Created by Łukasz Marczak

 * @since 17.02.16
 */
class PoiNearbyAdapter( var predictions: Array<Prediction>,  var callback: ItemClickCallback) : RecyclerView.Adapter<PoiNearbyAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, null, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.place.text = predictions[position].description
        holder.place.setOnClickListener { callback.onClick(predictions[position]) }
    }

    override fun getItemCount(): Int {
        return predictions.size
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var place: TextView

        init {
            place = itemView.findViewById(R.id.list_item_text) as TextView
        }
    }

    fun refreshWith(newArray: Array<Prediction>): Unit{
        predictions = newArray;
        notifyItemRangeChanged(0,newArray.size)
        notifyDataSetChanged()
    }
}
