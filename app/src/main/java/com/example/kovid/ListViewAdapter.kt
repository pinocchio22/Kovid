package com.example.kovid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-08-09
 * @desc
 */
class ListViewAdapter(val context : Context, val facList: ArrayList<List_item>): BaseAdapter() {

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        val item = facList[position]
        val facname = view?.findViewById<TextView>(R.id.name)

        facname?.text = item.facilityname
        return view!!
    }

    override fun getItem(position: Int): Any {
        return facList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return facList.size
    }
}
