
package com.example.limdongguk.myapplication

import android.media.AudioManager
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.limdongguk.myapplication.vo.DiaryVO
import kotlinx.android.synthetic.main.activity_alarm.*
import java.text.SimpleDateFormat

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        alarm_title.text = intent.extras.getString("title")
        alarm_content.text = intent.extras.getString("content")
        alarm_date.text = intent.extras.getString("start_date") +" ~ "+ intent.extras.getString("end_date")
    }
}
