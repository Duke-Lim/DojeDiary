package com.example.limdongguk.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.limdongguk.myapplication.adapter.CustomAdapter
import com.example.limdongguk.myapplication.adapter.CustomData
import com.example.limdongguk.myapplication.dao.DiaryDAO
import com.example.limdongguk.myapplication.util.voToJSON
import com.example.limdongguk.myapplication.vo.DiaryVO
import kotlinx.android.synthetic.main.activity_diary.*
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class ModifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        var diary_id: Int = getIntent().extras.getInt("id")

        var beforeDiaryVO: DiaryVO = DiaryDAO.getDiary(diary_id)

        var data: CustomData? = null

        var array_data: ArrayList<CustomData> = ArrayList<CustomData>()

        data = CustomData(R.drawable.ic_title_black_24dp, beforeDiaryVO.title)
        array_data.add(data)

        data = CustomData(R.drawable.ic_event_black_24dp, beforeDiaryVO.start_date.toString())
        array_data.add(data)

        data = CustomData(R.drawable.ic_add_alarm_black_24dp, beforeDiaryVO.start_time.toString())
        array_data.add(data)

        data = CustomData(R.drawable.ic_event_black_24dp, beforeDiaryVO.end_date.toString())
        array_data.add(data)

        data = CustomData(R.drawable.ic_add_alarm_black_24dp, beforeDiaryVO.end_time.toString())
        array_data.add(data)

        data = CustomData(R.drawable.ic_notifications_black_24dp, "알람 시간")
        array_data.add(data)

        data = CustomData(R.drawable.ic_note_add_black_24dp, beforeDiaryVO.content)
        array_data.add(data)

        var customList: ListView = custom_List as ListView
        var adapter: CustomAdapter = CustomAdapter(this, android.R.layout.simple_list_item_1, array_data)
        customList.adapter = adapter

        cancel.setOnClickListener { view ->
            this.finish()
        }

        check.setOnClickListener { view ->
            var list: ArrayList<String> = ArrayList<String>()
            var afterDiaryVO: DiaryVO = DiaryVO()

            for (i in 0..6) {
                if (i == 0 || i == 6) {
                    var temp: EditText = customList.getChildAt(i).findViewById(R.id.custom_list_title_main) as EditText

                    if (temp.text.toString() == "") {
                        this.finish()
                        break
                    }

                    list.add(temp.text.toString())
                } else {
                    var temp: TextView = customList.getChildAt(i).findViewById(R.id.date_title) as TextView

                    if (temp.text.toString() == "") {
                        this.finish()
                        break
                    }

                    list.add(temp.text.toString())
                }
            }
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.KOREA)
            val timestampFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
            val cal: Calendar = Calendar.getInstance()

            afterDiaryVO.id = diary_id
            afterDiaryVO.title = list.get(0)
            afterDiaryVO.start_date = Date(dateFormat.parse(list.get(1)).time)
            afterDiaryVO.start_time = Time(timeFormat.parse(list.get(2)).time)
            afterDiaryVO.end_date = Date(dateFormat.parse(list.get(3)).time)
            afterDiaryVO.end_time = Time(timeFormat.parse(list.get(4)).time)

            cal.time = Date(timestampFormat.parse(list.get(1) +" "+ list.get(2)).time)

            when (list.get(5)) {
                "10분 전" -> cal.add(Calendar.MINUTE, -10)

                "30분 전" -> cal.add(Calendar.MINUTE, -30)

                "1시간 전" -> cal.add(Calendar.HOUR, -1)

                "1일 전" -> cal.add(Calendar.DATE, -1)
            }

            afterDiaryVO.alarm_time = Timestamp(cal.timeInMillis)
            afterDiaryVO.content = list.get(6)

            var result: String = DiaryDAO.modifyDiary(voToJSON(afterDiaryVO))

            if (result == "성공") {
                Toast.makeText(this, "일정을 등록했습니다.", Toast.LENGTH_SHORT)
            } else {
                Toast.makeText(this, "일정등록을 실패했습니다. 다시시도해 주세요", Toast.LENGTH_SHORT)
            }

            this.finish()
        }

    }
}
