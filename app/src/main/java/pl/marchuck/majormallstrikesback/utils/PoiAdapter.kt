package pl.marchuck.majormallstrikesback.utils

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ListAdapter
import android.widget.TextView
import pl.marchuck.majormallstrikesback.R
import pl.marchuck.majormallstrikesback.model.poi.MatchedSubstring

import pl.marchuck.majormallstrikesback.model.poi.Prediction
import pl.marchuck.majormallstrikesback.model.poi.Term

/**
 * Created by Łukasz Marczak

 * @since 14.02.16
 */
class PoiAdapter(mDataSet: List<Prediction>) : Filterable, ListAdapter {
    var mEnabled = true
    var mDataSet: List<Prediction> = mDataSet;


    init {
        refreshAdapter(mDataSet)
    }

    fun refreshAdapter(dataSet: List<Prediction>) {
        this.mDataSet = dataSet
    }

    override fun areAllItemsEnabled(): Boolean {
        return mEnabled
    }

    override fun isEnabled(position: Int): Boolean {
        return mEnabled
    }

    override fun registerDataSetObserver(observer: DataSetObserver) {

    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {

    }

    override fun getCount(): Int {
        return mDataSet?.size
    }

    override fun getItem(position: Int): Prediction {
        if(mDataSet === null )return  getPrediction() else return mDataSet[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    fun getPrediction() : Prediction{
        val nullie = "null"

        val arr = arrayOf("","")
        val arr0 = arrayOf(MatchedSubstring(0,0))
        val arr1 = arrayOf(Term(0,""))

        val prediction = Prediction(nullie,nullie, arr0,nullie,nullie,arr1,arr)
        return prediction;
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView


        val mInflater = LayoutInflater.from(parent.context)
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item, null)
        }

        val text = convertView!!.findViewById(R.id.list_item_text) as TextView
        text.text = mDataSet[position].description
        return convertView
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getViewTypeCount(): Int {
        return 0
    }

    override fun isEmpty(): Boolean {
        return mDataSet.isEmpty()
    }

    override fun getFilter(): Filter? {
        return null
    }
}
