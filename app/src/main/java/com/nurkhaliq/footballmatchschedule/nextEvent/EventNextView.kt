package com.nurkhaliq.footballmatchschedule.nextEvent

import com.nurkhaliq.footballmatchschedule.model.Event

interface EventNextView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
}