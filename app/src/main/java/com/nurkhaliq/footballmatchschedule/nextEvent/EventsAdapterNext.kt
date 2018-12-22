package com.nurkhaliq.footballmatchschedule.nextEvent

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nurkhaliq.footballmatchschedule.R
import com.nurkhaliq.footballmatchschedule.model.Event

class EventsAdapterNext(private val events: List<Event>, private val listener: (Event) -> Unit) :
    RecyclerView.Adapter<EventsAdapterNext.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_view_layout, parent, false)
        return EventViewHolder(itemView)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindModel(events[position], listener)
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dateEvent: TextView = itemView.findViewById(R.id.date_event)
        val homeTeam: TextView = itemView.findViewById(R.id.home_name)
        val awayTeam: TextView = itemView.findViewById(R.id.away_name)
        val homeScore: TextView = itemView.findViewById(R.id.home_score)
        val awayScore: TextView = itemView.findViewById(R.id.away_score)

        fun bindModel(event: Event, listener: (Event) -> Unit) {
            dateEvent.text = event.dateEvent
            homeTeam.text = event.strHomeTeam
            awayTeam.text = event.strAwayTeam
            homeScore.text = event.intHomeScore
            awayScore.text = event.intAwayScore
            itemView.setOnClickListener {
                listener(event)
            }
        }


    }

}