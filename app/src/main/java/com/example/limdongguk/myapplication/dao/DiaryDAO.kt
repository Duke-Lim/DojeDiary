package com.example.limdongguk.myapplication.dao

import android.os.AsyncTask
import android.util.Log
import com.example.limdongguk.myapplication.vo.DiaryVO
import com.example.limdongguk.myapplication.util.jsonParse
import com.example.limdongguk.myapplication.util.listJSONParse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object DiaryDAO {
    var url = "http://192.168.35.29:8080/"
    //var url = "http://192.168.43.109:8080/"

    fun getAllDiary(): ArrayList<DiaryVO> {
        var jsonData: String = ""

        AsyncTask.execute(Runnable {
            var conn = URL(url).openConnection() as HttpURLConnection

            jsonData = conn.inputStream.bufferedReader().readLine()

            conn.disconnect()
        })

        while (jsonData.equals("")) {}

        return listJSONParse(jsonData)
    }

    fun getDiary(id: Int): DiaryVO {
        var jsonData: String = ""

        AsyncTask.execute(Runnable {
            var conn = URL(url + id).openConnection() as HttpURLConnection

            jsonData = conn.inputStream.bufferedReader().readLine()

            conn.disconnect()
        })

        while (jsonData.equals("")) {}

        Log.i("json", jsonData)

        return jsonParse(jsonData)
    }

    fun insertDiary(jsonData: String): String {
        var result: String = ""

        AsyncTask.execute(Runnable {
            var conn = URL(url).openConnection() as HttpURLConnection

            conn.requestMethod = "POST"
            conn.doInput = true
            conn.doOutput = true

            conn.setRequestProperty("Content-Type", "application/json")

            conn.outputStream.write(jsonData.toByteArray(Charsets.UTF_8))
            conn.outputStream.flush()
            conn.outputStream.close()

            var buffReader = BufferedReader(InputStreamReader(conn.inputStream, "UTF-8"), conn.contentLength)

            result = buffReader.readLine()

            conn.disconnect()
        })

        while (result.equals("")) {}

        return result
    }

    fun modifyDiary(jsonData: String): String {
        var result: String = ""

        AsyncTask.execute(Runnable {
            var conn = URL(url).openConnection() as HttpURLConnection

            conn.requestMethod = "PUT"
            conn.doInput = true
            conn.doOutput = true

            conn.setRequestProperty("Content-Type", "application/json")

            conn.outputStream.write(jsonData.toByteArray(Charsets.UTF_8))
            conn.outputStream.flush()
            conn.outputStream.close()

            var buffReader = BufferedReader(InputStreamReader(conn.inputStream, "UTF-8"), conn.contentLength)

            result = buffReader.readLine()

            conn.disconnect()
        })

        while (result.equals("")) {}

        return result
    }

    fun deleteDiary(id: Int): String {
        var result: String = ""

        AsyncTask.execute(Runnable {
            var conn = URL(url + id).openConnection() as HttpURLConnection

            conn.requestMethod = "DELETE"

            result = conn.inputStream.bufferedReader().readLine()

            conn.disconnect()
        })

        while (result.equals("")) {}

        return result
    }
}



