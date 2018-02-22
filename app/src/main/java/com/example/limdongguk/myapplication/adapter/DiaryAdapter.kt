package com.example.limdongguk.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.limdongguk.myapplication.R

class DiaryAdapter(context: Context, id: Int, items: ArrayList<DiaryData>) : ArrayAdapter<DiaryData>(context, id, items) {
    var m_Context: Context? = null

    init {
        this.m_Context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var pView: PointView
        var view: View? = convertView

        if (view == null) {
            view = LayoutInflater.from(m_Context).inflate(R.layout.diary_view, null)

            pView = PointView(view)

            view.tag = pView
        }

        pView = view?.tag as PointView

        var diaryData: DiaryData = getItem(position)

        if (diaryData != null) {
            pView.getIconView()?.setBackgroundResource(diaryData.image_id)
            pView.getTitleView()?.setText(diaryData.main_title)
            pView.getSubTitleView()?.setText(diaryData.sub_title)
            pView.getIdView()?.setText(diaryData.id.toString())
        }

        return view

    }

}

class PointView(baseView: View) {
    var baseView: View? = null
    var ivIcon: ImageView? = null
    var tvMainTitle: TextView? =  null
    var tvSubTitle: TextView? = null
    var tvId: TextView? = null

    init {
        this.baseView = baseView
    }

    fun getIconView(): ImageView? {
        if (ivIcon == null) {
            ivIcon = baseView?.findViewById(R.id.diary_icon) as ImageView
        }

        return ivIcon
    }

    fun getTitleView(): TextView? {
        if (tvMainTitle == null) {
            tvMainTitle = baseView?.findViewById(R.id.diary_title) as TextView
        }

        return tvMainTitle
    }

    fun getSubTitleView(): TextView? {
        if (tvSubTitle == null) {
            tvSubTitle = baseView?.findViewById(R.id.diary_sub_title) as TextView
        }

        return tvSubTitle
    }

    fun getIdView(): TextView? {
        if (tvId == null) {
            tvId = baseView?.findViewById(R.id.diary_id) as TextView
        }

        return tvId
    }

}

data class DiaryData (
        var image_id: Int,
        var main_title: String?,
        var sub_title: String?,
        var id: Int?
)