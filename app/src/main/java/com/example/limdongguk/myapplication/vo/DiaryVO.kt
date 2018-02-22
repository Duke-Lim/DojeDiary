package com.example.limdongguk.myapplication.vo

import java.io.Serializable
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

data class DiaryVO(
    var id: Int? = null,
    var title: String? = null,
    var content: String? = null,
    var created: Timestamp? = null,
    var start_date: Date? = null,
    var end_date: Date? = null,
    var start_time: Time? = null,
    var end_time: Time? = null,
    var alarm_time: Timestamp? = null
)