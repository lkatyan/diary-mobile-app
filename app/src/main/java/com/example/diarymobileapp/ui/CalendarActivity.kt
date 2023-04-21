package com.example.diarymobileapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diarymobileapp.R
import com.example.diarymobileapp.data.Db
import com.example.diarymobileapp.models.Item
import com.example.diarymobileapp.models.ItemForToDoList
import com.example.diarymobileapp.ui.adapters.CalendarAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class CalendarActivity : AppCompatActivity(), CalendarAdapter.Listener {

    lateinit var calendar: CalendarView
    lateinit var text: TextView
    lateinit var recyclerView: RecyclerView
    private val adapter = CalendarAdapter(this)

    var listItems = ArrayList<String>()

    var timestampStart by Delegates.notNull<Long>()
    var timestampFinish by Delegates.notNull<Long>()
    var dataStart: Calendar = Calendar.getInstance()
    var dateOfDay: Calendar =
        Calendar
            .Builder()
            .build()
    var dateOfFinishDay: Calendar =
        Calendar
            .Builder()
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val db = Db.getDb(this)

        //18 april 0:33
        val item = Item(
            null,
            1681778000,
            1681865159,
            "FirstWork",
            "DescriptionOfFirstWork"
        )

        //18 april 6:06
        val item2 = Item(
            null,
            1681798000,
            1681885259,
            "SecondWork",
            "DescriptionOfSecondWork"
        )

        Thread{
            db.getDao().insertItem(item)
            db.getDao().insertItem(item2)
        }.start()

        calendar = findViewById(R.id.calendarView)
        text = findViewById(R.id.textView)

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->

            listItems.clear()
            dateOfDay.clear()
            dateOfFinishDay.clear()

            dateOfDay.set(year, month, dayOfMonth)
            timestampStart = dateOfDay.timeInMillis / 1000

            dateOfFinishDay = dateOfDay
            dateOfFinishDay.add(Calendar.HOUR, 23)
            dateOfFinishDay.add(Calendar.MINUTE, 59)
            timestampFinish = dateOfFinishDay.timeInMillis / 1000

            // Recycler обновляется с задержкой из-за этого
            db.getDao()
                .getItems(timestampStart, timestampFinish)
                .asLiveData()
                .observe(this) { list ->
                    list.forEach {
                        dataStart.timeInMillis = it.date_start?.times(1000)!!
                        var item = ""
                        if (dataStart.get(Calendar.HOUR)<10) {
                            item += "0${dataStart.get(Calendar.HOUR)}:"
                        } else { item += "${dataStart.get(Calendar.HOUR)}:" }
                        if (dataStart.get(Calendar.MINUTE)<10) {
                            item += "0${dataStart.get(Calendar.MINUTE)} "
                        } else { item += "${dataStart.get(Calendar.MINUTE)} " }
                        item += "${it.name} \n"
                        listItems.add(item)
                    }
                }
            init(listItems)

        }
    }

    private fun init(listItems: ArrayList<String>) {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this@CalendarActivity)
        recyclerView.adapter = adapter
        adapter.clearAdapter()

        val intervalsList = listOf(
            "00:00 - 01:00",
            "01:00 - 02:00",
            "02:00 - 03:00",
            "03:00 - 04:00",
            "04:00 - 05:00",
            "05:00 - 06:00",
            "06:00 - 07:00",
            "07:00 - 08:00",
            "08:00 - 09:00",
            "09:00 - 10:00",
            "10:00 - 11:00",
            "11:00 - 12:00",
            "12:00 - 13:00",
            "13:00 - 14:00",
            "14:00 - 15:00",
            "15:00 - 16:00",
            "16:00 - 17:00",
            "17:00 - 18:00",
            "18:00 - 19:00",
            "19:00 - 20:00",
            "20:00 - 21:00",
            "21:00 - 22:00",
            "22:00 - 23:00",
            "23:00 - 00:00"
        )

        for (i in intervalsList.indices) {
            lateinit var it: ItemForToDoList

            if (listItems.size > 0) {
                var fewToDo = ""
                when (i) {
                    0 -> fewToDo=checkList("00")
                    1 -> fewToDo=checkList("01")
                    2 -> fewToDo=checkList("02")
                    3 -> fewToDo=checkList("03")
                    4 -> fewToDo=checkList("04")
                    5 -> fewToDo=checkList("05")
                    6 -> fewToDo=checkList("06")
                    7 -> fewToDo=checkList("07")
                    8 -> fewToDo=checkList("08")
                    9 -> fewToDo=checkList("09")
                    10 -> fewToDo=checkList("10")
                    11 -> fewToDo=checkList("11")
                    12 -> fewToDo=checkList("12")
                    13 -> fewToDo=checkList("13")
                    14 -> fewToDo=checkList("14")
                    15 -> fewToDo=checkList("15")
                    16 -> fewToDo=checkList("16")
                    17 -> fewToDo=checkList("17")
                    18 -> fewToDo=checkList("18")
                    19 -> fewToDo=checkList("19")
                    20 -> fewToDo=checkList("20")
                    21 -> fewToDo=checkList("21")
                    22 -> fewToDo=checkList("22")
                    23 -> fewToDo=checkList("23")
                    else -> fewToDo+=""
                }
                it = ItemForToDoList(intervalsList[i], fewToDo)
            } else {
                it = ItemForToDoList(intervalsList[i], "")
            }
            adapter.addItem(it)
        }
    }

    private fun checkList(checkString: String): String {
        var resultString = ""
        for (j in 0 until listItems.size) {
            val substr = listItems[j].substringBefore(':')
            if (substr==checkString) resultString+=listItems[j]
        }
        return resultString
    }

    override fun onDestroy() {
        super.onDestroy()
        finishAffinity()
    }

    override fun onClick(it: ItemForToDoList) {
        val intent = Intent(this@CalendarActivity, DetailsActivity::class.java).apply {
            putExtra("hourOfToDo", it.time_interval)
            putExtra("timeStart", timestampStart)
            putExtra("timeFinish", timestampFinish)
        }
        startActivity(intent)
    }
}