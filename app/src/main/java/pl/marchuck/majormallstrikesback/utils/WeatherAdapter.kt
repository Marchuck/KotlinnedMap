package pl.marchuck.majormallstrikesback.utils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import pl.marchuck.majormallstrikesback.R

/**
 * Created by Łukasz Marczak

 * @since 14.02.16
 */
class WeatherAdapter(internal var dataSet: Array<String?>) : RecyclerView.Adapter<WeatherAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, null, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.textView.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        var textView: TextView

        init {
            textView = v.findViewById(R.id.list_item_text) as TextView
        }
    }
}
