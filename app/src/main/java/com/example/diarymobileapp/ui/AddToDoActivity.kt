package com.example.diarymobileapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.diarymobileapp.R
import com.example.diarymobileapp.data.Db
import com.example.diarymobileapp.databinding.ActivityAddToDoBinding
import com.example.diarymobileapp.models.Item
import java.util.*

class AddToDoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddToDoBinding
    lateinit var calendar: CalendarView

    var dataStart: Calendar =
        Calendar
            .Builder()
            .build()
    var dataFinish: Calendar =
        Calendar
            .Builder()
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calendar = findViewById(R.id.calendarViewAddToDo)
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            dataStart.set(year, month, dayOfMonth)
            dataFinish.set(year, month, dayOfMonth)
        }
    }

    fun onClickAdd(view: View) {
        val db = Db.getDb(this)

        binding.apply {

            dataStart.add(Calendar.HOUR, timePickerAddToDo.hour)
            dataStart.add(Calendar.MINUTE, timePickerAddToDo.minute)

            dataFinish.add(Calendar.HOUR, timePickerAddToDo.hour)
            dataFinish.add(Calendar.MINUTE, timePickerAddToDo.minute)
            dataFinish.add(Calendar.MINUTE, 20)

            val title = editTextTitleToDo.text.toString()
            val description = editTextDescriptionToDo.text.toString()

            val item = Item(
                null,
                dataStart.timeInMillis/1000,
                dataFinish.timeInMillis/1000,
                title,
                description
            )

            if (item.name!="" && item.description!="") {
                Thread{
                    db.getDao().insertItem(item)
                }.start()
            }
        }
        finish()
    }
}