package com.nurkhaliq.footballmatchschedule.pastEvent

import com.google.gson.Gson
import com.nurkhaliq.footballmatchschedule.CoroutineContextProvider
import com.nurkhaliq.footballmatchschedule.api.ApiRepository
import com.nurkhaliq.footballmatchschedule.api.TheSportDBApi
import com.nurkhaliq.footballmatchschedule.model.EventResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventPastPresenter(
    private val view: EventPastView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getEventList(id: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEvents(id)).await(),
                EventResponse::class.java
            )


            view.hideLoading()
            view.showEventList(data.events)
        }
    }
}
