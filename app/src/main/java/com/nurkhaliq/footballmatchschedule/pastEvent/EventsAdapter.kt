package com.nurkhaliq.footballmatchschedule.pastEvent

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nurkhaliq.footballmatchschedule.R
import com.nurkhaliq.footballmatchschedule.model.Event

class EventsAdapter(private val events: List<Event>, private val listener: (Event) -> Unit) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_view_layout, parent, false)
        return EventViewHolder(itemView)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindModel(events[position], listener)
    }


    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dateEvent: TextView = itemView.findViewById(R.id.date_event)
        private val homeTeam: TextView = itemView.findViewById(R.id.home_name)
        private val homeScore: TextView = itemView.findViewById(R.id.home_score)
        private val awayTeam: TextView = itemView.findViewById(R.id.away_name)
        private val awayScore: TextView = itemView.findViewById(R.id.away_score)

        fun bindModel(event: Event, listener: (Event) -> Unit) {
            dateEvent.text = event.dateEvent
            homeTeam.text = event.strHomeTeam
            homeScore.text = event.intHomeScore.toString()
            awayTeam.text = event.strAwayTeam
            awayScore.text = event.intAwayScore.toString()
            itemView.setOnClickListener {
                listener(event)
            }
        }


    }

}

