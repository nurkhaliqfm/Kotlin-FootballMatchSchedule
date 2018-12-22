package com.nurkhaliq.footballmatchschedule.detail

import com.nurkhaliq.footballmatchschedule.model.Event
import com.nurkhaliq.footballmatchschedule.model.Team

interface EventDetailView {
    fun showLoading()
    fun hideLoading()
    fun showEventDetail(data: List<Event>, teamHome: List<Team>, teamAway: List<Team>)
}