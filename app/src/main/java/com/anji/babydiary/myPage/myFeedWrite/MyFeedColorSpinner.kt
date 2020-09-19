package com.anji.babydiary.myPage.myFeedWrite

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.anji.babydiary.R

class MyFeedColorSpinner (val context: Context, var listItemsTxt: Array<String>) : BaseAdapter() {
    val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.myfeed_color_drop_down, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }



        vh.button.setBackgroundColor(Color.parseColor(listItemsTxt.get(position)))
        //vh.label.text = listItemsTxt.get(position)
        return view
    }

    override fun getItem(position: Int): Any? {

        return null

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {
        return listItemsTxt.size
    }

    private class ItemRowHolder(row: View?) {

        val button: TextView

        init {
            this.button = row?.findViewById(R.id.colorButton) as TextView
        }
    }
}