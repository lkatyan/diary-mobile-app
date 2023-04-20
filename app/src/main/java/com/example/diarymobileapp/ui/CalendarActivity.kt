package com.example.diarymobileapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.lifecycle.asLiveData
import com.example.diarymobileapp.R
import com.example.diarymobileapp.data.Db
import com.example.diarymobileapp.models.Item
import java.util.*

class CalendarActivity : AppCompatActivity() {

    lateinit var calendar: CalendarView
    lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)


        val db = Db.getDb(this)
        val item = Item(
            null,
            1681778000,
            1681862300,
            "FirstWork",
            "DescriptionOfFirstWork"
        )
        Thread{
            db.getDao().InsertItem(item)
        }.start()


        calendar = findViewById(R.id.calendarView)
        text = findViewById(R.id.textView)
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var dateOfDay: Calendar =
                Calendar
                    .Builder()
                    .setDate(year, month, dayOfMonth)
                    .build()
            var timestampStart: Long = dateOfDay.timeInMillis / 1000
            dateOfDay.add(Calendar.HOUR, 23)
            dateOfDay.add(Calendar.MINUTE, 59)
            var timestampFinish: Long = dateOfDay.timeInMillis / 1000

            text.text = "\n $timestampStart - $timestampFinish"

            db.getDao()
                .getItems(timestampStart, timestampFinish)
                .asLiveData()
                .observe(this) { list ->
                    text.text = ""
                    list.forEach {
                        var dataStart: Calendar = Calendar.getInstance()
                        dataStart.timeInMillis = it.date_start?.times(1000)!!
                        var dataFinish: Calendar = Calendar.getInstance()
                        dataFinish.timeInMillis = it.date_finish?.times(1000)!!
                        var item = "\n ${dataStart.get(Calendar.HOUR)}: " +
                                "${dataStart.get(Calendar.MINUTE)} " +
                                "${it.name} \n"
                        text.append(item)
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finishAffinity()
    }
}