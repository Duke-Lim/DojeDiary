package com.example.limdongguk.myapplication.adapter

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.limdongguk.myapplication.MainActivity

import com.example.limdongguk.myapplication.R
import java.text.SimpleDateFormat
import java.util.*

class CustomAdapter(context: Context, id: Int, items: ArrayList<CustomData>) : ArrayAdapter<CustomData>(context, id, items) {
    var m_Context: Context? = null

    init {
        this.m_Context = context
    }

    var cal: Calendar = Calendar.getInstance()
    var year: Int = cal.get(Calendar.YEAR)
    var month: Int = cal.get(Calendar.MONTH)
    var day_of_month: Int = cal.get(Calendar.DAY_OF_MONTH)
    var hour_of_day: Int = cal.get(Calendar.HOUR_OF_DAY)
    var minute: Int = cal.get(Calendar.MINUTE)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var pView: PointerView
        var dView: DatePointerView
        var view: View? = convertView

        if (view == null) {
            if (position == 0 || position == 6) {
                view = LayoutInflater.from(m_Context).inflate(R.layout.custom_view, null)

                pView = PointerView(view)

                view.tag = pView

                pView = view?.tag as PointerView

                var customData: CustomData = getItem(position)

                if (customData != null) {
                    pView.getIconView()?.setBackgroundResource(customData.image_id)
                    pView.getTitleView()?.setHint(customData.main_title)
                }

            } else {
                view = LayoutInflater.from(m_Context).inflate(R.layout.date_view, null)

                dView = DatePointerView(view)

                view.tag = dView

                dView = view?.tag as DatePointerView

                var customData: CustomData = getItem(position)

                if (customData != null) {
                    dView.getIconView()?.setBackgroundResource(customData.image_id)
                    dView.getTitleView()?.setText(customData.main_title)
                }
            }
        }

        view.setOnClickListener { view ->
            if (position == 5) {
                val items = arrayOf<String>("정시", "10분 전", "30분 전", "1시간 전", "1일 전")

                var dialog: AlertDialog.Builder = AlertDialog.Builder(context)

                dialog.setTitle("알람 시간")
                dialog.setSingleChoiceItems(items, 0, DialogInterface.OnClickListener { dialogInterface, which ->
                    var tv:TextView =  parent?.getChildAt(position)?.findViewById(R.id.date_title) as TextView

                    tv.setText(items[which])

                })
                dialog.create()
                dialog.show()

            } else if (position == 1 || position == 3) {
                DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val myFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

                    var returnString: String = myFormat.format(cal.time)

                    var tv:TextView =  parent?.getChildAt(position)?.findViewById(R.id.date_title) as TextView

                    tv.setText(returnString)
                }, year, month, day_of_month).show()
            } else {
                TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    cal.set(Calendar.MINUTE, minute)

                    val myFormat = SimpleDateFormat("HH:mm:ss", Locale.KOREA)

                    var returnString: String = myFormat.format(cal.time)

                    var tv:TextView =  parent?.getChildAt(position)?.findViewById(R.id.date_title) as TextView

                    tv.setText(returnString)
                }, hour_of_day, minute, true).show()

            }
        }

        return view
    }

}

class DatePointerView(baseView: View) {
    var baseView: View? = null
    var ivIcon: ImageView? = null
    var tvTitle: TextView? =  null

    init {
        this.baseView = baseView
    }

    fun getIconView(): ImageView? {
        if (ivIcon == null) {
            ivIcon = baseView?.findViewById(R.id.date_icon) as ImageView
        }

        return ivIcon
    }

    fun getTitleView(): TextView? {
        if (tvTitle == null) {
            tvTitle = baseView?.findViewById(R.id.date_title) as TextView
        }

        return tvTitle
    }
}

class PointerView(baseView: View) {
    var baseView: View? = null
    var ivIcon: ImageView? = null
    var tvTitle: EditText? =  null

    init {
        this.baseView = baseView
    }

    fun getIconView(): ImageView? {
        if (ivIcon == null) {
            ivIcon = baseView?.findViewById(R.id.custom_list_image) as ImageView
        }

        return ivIcon
    }

    fun getTitleView(): EditText? {
        if (tvTitle == null) {
            tvTitle = baseView?.findViewById(R.id.custom_list_title_main) as EditText
        }

        return tvTitle
    }
}

data class CustomData (
        var image_id: Int,
        var main_title: String?
)