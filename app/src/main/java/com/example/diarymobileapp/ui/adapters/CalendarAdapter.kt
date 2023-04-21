package com.example.diarymobileapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diarymobileapp.R
import com.example.diarymobileapp.databinding.ItemForToDoListBinding
import com.example.diarymobileapp.models.ItemForToDoList
import java.util.ArrayList

class CalendarAdapter(val listener: Listener): RecyclerView.Adapter<CalendarAdapter.CalendarHolder>() {

    private val itemCalendarList = ArrayList<ItemForToDoList>()

    class CalendarHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = ItemForToDoListBinding.bind(item)

        fun bind(itemList: ItemForToDoList, listener: Listener) = with(binding) {
            textViewInterval.text = itemList.time_interval
            textViewToDo.text = itemList.toDo
            itemView.setOnClickListener {
                listener.onClick(itemList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_for_to_do_list, parent, false)
        return CalendarHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        holder.bind(itemCalendarList[position], listener)
    }

    override fun getItemCount(): Int {
        return itemCalendarList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(it: ItemForToDoList) {
        itemCalendarList.add(it)
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        itemCalendarList.clear()
    }

    interface Listener {
        fun onClick(it: ItemForToDoList)
    }

}