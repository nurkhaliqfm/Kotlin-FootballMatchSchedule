package com.nurkhaliq.footballmatchschedule.pastEvent

import com.nurkhaliq.footballmatchschedule.model.Event

interface EventPastView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
}