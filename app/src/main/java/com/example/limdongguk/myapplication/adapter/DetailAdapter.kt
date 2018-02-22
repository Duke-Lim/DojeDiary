package com.example.limdongguk.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.limdongguk.myapplication.R
import java.util.ArrayList

class DetailAdapter(context: Context, id: Int, items: ArrayList<CustomData>) : ArrayAdapter<CustomData>(context, id, items) {
    var m_Context: Context? = null

    init {
        this.m_Context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var dView: DatePointerView
        var view: View? = convertView

        view = LayoutInflater.from(m_Context).inflate(R.layout.date_view, null)

        dView = DatePointerView(view)

        view.tag = dView

        dView = view?.tag as DatePointerView

        var customData: CustomData = getItem(position)

        if (customData != null) {
            dView.getIconView()?.setBackgroundResource(customData.image_id)
            dView.getTitleView()?.setText(customData.main_title)
        }

        return view
    }

}