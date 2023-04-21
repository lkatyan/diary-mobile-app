package com.example.diarymobileapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.diarymobileapp.R
import com.example.diarymobileapp.data.Db
import com.example.diarymobileapp.databinding.ActivityDetailsBinding
import com.example.diarymobileapp.models.Item
import com.example.diarymobileapp.models.ItemForToDoList
import java.util.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dayStart = intent.getLongExtra("timeStart", 946684800 )
        val dayFinish = intent.getLongExtra("timeFinish", 946771199)

        val hourOfToDo = intent.getStringExtra("hourOfToDo")
        binding.apply {
            textViewIntervalDetails.text = hourOfToDo
        }

        var dataStart: Calendar = Calendar.getInstance()
        var dataFinish: Calendar = Calendar.getInstance()

        val db = Db.getDb(this)
        db.getDao()
            .getItems(dayStart, dayFinish)
            .asLiveData()
            .observe(this) { list ->
                list.forEach {
                    dataStart.timeInMillis = it.date_start?.times(1000)!!
                    dataFinish.timeInMillis = it.date_finish?.times(1000)!!
                    var item = ""

                    binding.apply {

                        // Преобразую дату начала дела в читаемый вид..
                        if (dataStart.get(Calendar.HOUR)<10) {
                            item += "0${dataStart.get(Calendar.HOUR)}:"
                        } else { item += "${dataStart.get(Calendar.HOUR)}:" }
                        if (dataStart.get(Calendar.MINUTE)<10) {
                            item += "0${dataStart.get(Calendar.MINUTE)} "
                        } else { item += "${dataStart.get(Calendar.MINUTE)} - " }

                        if (item!="" && item.substringBefore(":") == hourOfToDo.toString().substringBefore(":")) {

                            textViewStartToDo.text = item
                            item = ""

                            // Преобразую дату окончания дела в читаемый вид..
                            if (dataFinish.get(Calendar.HOUR)<10) {
                                item += "0${dataFinish.get(Calendar.HOUR)}:"
                            } else { item += "${dataFinish.get(Calendar.HOUR)}:" }
                            if (dataFinish.get(Calendar.MINUTE)<10) {
                                item += "0${dataFinish.get(Calendar.MINUTE)} "
                            } else { item += "${dataFinish.get(Calendar.MINUTE)} " }

                            textViewFinishToDo.text = item
                            item = ""

                            textViewTitleToDo.text = it.name
                            textViewDescriptionToDo.text = it.description
                            layoutOfToDoDetails.setBackgroundColor(R.color.teal_200)
                        }
                    }
                }
            }
    }
}