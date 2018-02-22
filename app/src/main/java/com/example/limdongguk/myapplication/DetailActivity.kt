package com.example.limdongguk.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.limdongguk.myapplication.adapter.CustomAdapter
import com.example.limdongguk.myapplication.adapter.CustomData
import com.example.limdongguk.myapplication.adapter.DetailAdapter
import com.example.limdongguk.myapplication.dao.DiaryDAO
import com.example.limdongguk.myapplication.vo.DiaryVO
import kotlinx.android.synthetic.main.activity_diary.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var diary_id: Int = getIntent().extras.getInt("id")

        var diaryVO: DiaryVO = DiaryDAO.getDiary(diary_id)

        var data: CustomData? = null

        var array_data: ArrayList<CustomData> = ArrayList<CustomData>()

        data = CustomData(R.drawable.ic_title_black_24dp, diaryVO.title)
        array_data.add(data)

        data = CustomData(R.drawable.ic_event_black_24dp, diaryVO.start_date.toString())
        array_data.add(data)

        data = CustomData(R.drawable.ic_add_alarm_black_24dp, diaryVO.start_time.toString())
        array_data.add(data)

        data = CustomData(R.drawable.ic_event_black_24dp, diaryVO.end_date.toString())
        array_data.add(data)

        data = CustomData(R.drawable.ic_add_alarm_black_24dp, diaryVO.end_time.toString())
        array_data.add(data)

        data = CustomData(R.drawable.ic_notifications_black_24dp, "알람 시간")
        array_data.add(data)

        data = CustomData(R.drawable.ic_note_add_black_24dp, diaryVO.content)
        array_data.add(data)

        var customList: ListView = custom_List as ListView
        var adapter: DetailAdapter = DetailAdapter(this, android.R.layout.simple_list_item_1, array_data)
        customList.adapter = adapter

        cancel.setOnClickListener { view ->
            this.finish()
        }

        check.setOnClickListener { view ->
            this.finish()
        }
    }
}
