package com.example.limdongguk.myapplication

import android.app.Activity
import android.content.Context
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.example.limdongguk.myapplication.adapter.DiaryAdapter
import com.example.limdongguk.myapplication.adapter.DiaryData
import com.example.limdongguk.myapplication.dao.DiaryDAO
import com.example.limdongguk.myapplication.vo.DiaryVO
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.model.CalendarEvent
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.activity_diary.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_view.view.*
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var diaryList: List<DiaryVO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val mainActivity: MainActivity = this

        fab.setOnClickListener { view ->
            var intent = Intent(this, DiaryActivity::class.java)
            startActivity(intent)
        }

        diaryList = DiaryDAO.getAllDiary()

        diaryList?.forEach { vo ->
            if (vo.alarm_time!!.time >= System.currentTimeMillis()) {
                setAlarm(vo)
            }
        }

        var startDate: Calendar = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)

        var endDate: Calendar = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)

        var horizontalCalendar: HorizontalCalendar = HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .addEvents(object : CalendarEventsPredicate {
                    override fun events(date: Calendar): List<CalendarEvent> {
                        var eventList: ArrayList<CalendarEvent> = ArrayList<CalendarEvent>()

                        diaryList?.forEach { vo ->
                            if (date.timeInMillis in vo.start_date!!.time..vo.end_date!!.time+100) {
                                eventList.add(CalendarEvent(Color.RED, vo.title))
                            }
                        }

                        return eventList
                    }
                })
                .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar, position: Int) {
                var combineTime: String

                var data: DiaryData

                var array_data: ArrayList<DiaryData> = ArrayList<DiaryData>()

                var mainList: ListView = main_List

                var adapter: DiaryAdapter = DiaryAdapter(mainActivity, android.R.layout.simple_list_item_1, array_data)

                diaryList?.forEach { vo ->
                    if (date.timeInMillis in vo.start_date!!.time..vo.end_date!!.time) {
                        combineTime = vo.start_time.toString() +" ~ "+ vo.end_time.toString()

                        data = DiaryData(R.drawable.ic_light_blue_box, vo.title, combineTime, vo.id)
                        array_data.add(data)
                    }
                }
                if (array_data.isEmpty() == false) {
                    adapter = DiaryAdapter(mainActivity, android.R.layout.simple_list_item_1, array_data)
                    mainList.adapter = adapter
                } else {
                    adapter = DiaryAdapter(mainActivity, android.R.layout.simple_list_item_1, array_data)
                    mainList.adapter = adapter
                }
            }
        }

        main_List.setOnItemClickListener { adapterView, view, i, l ->
            val items = arrayOf<String>("자세히 보기", "수정", "삭제")
            var diary_id: Int = main_List.getChildAt(i).findViewById<TextView>(R.id.diary_id).text.toString().toInt()
            var intent: Intent

            var dialog = AlertDialog.Builder(this)

            dialog.setItems(items, DialogInterface.OnClickListener { dialogInterface, which ->
                when (which) {
                    0 -> {
                        intent = Intent(mainActivity, DetailActivity::class.java)
                        intent.putExtra("id", diary_id)
                        startActivity(intent)
                    }

                    1 -> {
                        intent = Intent(mainActivity, ModifyActivity::class.java)
                        intent.putExtra("id", diary_id)
                        startActivity(intent)
                    }

                    2 -> {
                        Log.i("log", "log")
                        var confirm = AlertDialog.Builder(mainActivity)

                        confirm.setMessage("정말로 삭제하시겠습니까?")
                        confirm.setPositiveButton("예", DialogInterface.OnClickListener { dialogInterface, i ->
                            DiaryDAO.deleteDiary(diary_id)

                            main_List.deferNotifyDataSetChanged()

                            intent = getIntent()
                            finish()
                            startActivity(intent)

                            confirm.create().dismiss()
                            dialog.create().dismiss()
                        })

                        confirm.setNegativeButton("아니오", DialogInterface.OnClickListener { dialogInterface, i ->
                            confirm.create().dismiss()
                            dialog.create().dismiss()
                        })

                        confirm.create()
                        confirm.show()
                    }
                }
            })
            dialog.create()
            dialog.show()
        }

    }

    fun setAlarm(vo: DiaryVO) {
        var alarmManager: AlarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        var intent: Intent = Intent(this, AlarmActivity::class.java)
        intent.putExtra("title", vo.title)
        intent.putExtra("content", vo.content)
        intent.putExtra("start_date", dateFormat.format(vo.start_date))
        intent.putExtra("end_date", dateFormat.format(vo.end_date))

        var pIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.set(AlarmManager.RTC, vo.alarm_time!!.time, pIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
