package com.nurkhaliq.footballmatchschedule.favorite

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nurkhaliq.footballmatchschedule.R

class FavoriteEventsAdapter(private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit) :
    RecyclerView.Adapter<FavoriteEventsAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_view_layout, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }


    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dateEvent: TextView = itemView.findViewById(R.id.date_event)
        private val homeTeam: TextView = itemView.findViewById(R.id.home_name)
        private val awayTeam: TextView = itemView.findViewById(R.id.away_name)
        private val homeScore: TextView = itemView.findViewById(R.id.home_score)
        private val awayScore: TextView = itemView.findViewById(R.id.away_score)

        fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
            dateEvent.text = favorite.dateEvent
            homeTeam.text = favorite.strHomeTeam
            awayTeam.text = favorite.strAwayTeam
            homeScore.text = favorite.intHomeScore
            awayScore.text = favorite.intAwayScore
            itemView.setOnClickListener {
                listener(favorite)
            }
        }
    }

}
