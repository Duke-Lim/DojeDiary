package com.example.limdongguk.myapplication.util

import com.example.limdongguk.myapplication.vo.DiaryVO
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat

fun jsonParse(jsonData: String): DiaryVO {
    var jsonObject = JSONObject(jsonData)

    return DiaryVO(
            jsonObject.getInt("id"),
            jsonObject.getString("title"),
            jsonObject.getString("content"),
            toTimeStamp(jsonObject.getLong("created")),
            Date.valueOf(jsonObject.getString("start_date")),
            Date.valueOf(jsonObject.getString("end_date")),
            Time.valueOf(jsonObject.getString("start_time")),
            Time.valueOf(jsonObject.getString("end_time")),
            toTimeStamp(jsonObject.getLong("alarm_time"))
    )
}

fun listJSONParse(jsonData: String): ArrayList<DiaryVO> {
    var jsonArray = JSONArray(jsonData)
    var temp = ArrayList<DiaryVO>()

    for (i in 0 until jsonArray.length()) {
        var jsonObject = jsonArray.getJSONObject(i)

        var vo = DiaryVO(
                jsonObject.getInt("id"),
                jsonObject.getString("title"),
                jsonObject.getString("content"),
                toTimeStamp(jsonObject.getLong("created")),
                Date.valueOf(jsonObject.getString("start_date")),
                Date.valueOf(jsonObject.getString("end_date")),
                Time.valueOf(jsonObject.getString("start_time")),
                Time.valueOf(jsonObject.getString("end_time")),
                toTimeStamp(jsonObject.getLong("alarm_time"))
        )

        temp.add(vo)

    }

    return temp
}

fun voToJSON(diaryVO: DiaryVO): String {
    return "{\"id\":\"${diaryVO.id}\",\"title\":\"${diaryVO.title}\",\"content\":\"${diaryVO.content}\",\"start_date\":\"${diaryVO.start_date}\",\"end_date\":\"${diaryVO.end_date}\",\"start_time\":\"${diaryVO.start_time}\",\"end_time\":\"${diaryVO.end_time}\",\"alarm_time\":\"${diaryVO.alarm_time}\"}"
}

fun toTimeStamp(timestamp: Long): Timestamp {
    return Timestamp.valueOf(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(timestamp)))
}
