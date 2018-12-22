package com.nurkhaliq.footballmatchschedule.detail

import com.google.gson.Gson
import com.nurkhaliq.footballmatchschedule.CoroutineContextProvider
import com.nurkhaliq.footballmatchschedule.api.ApiRepository
import com.nurkhaliq.footballmatchschedule.api.TheSportDBApi
import com.nurkhaliq.footballmatchschedule.model.EventResponse
import com.nurkhaliq.footballmatchschedule.model.TeamResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventDetailPresenter(
    private val view: EventDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getEventDetail(id: String?, teamHomeId: String?, teamAwayId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventDetail(id)).await(),
                EventResponse::class.java
            )

            val teamHome = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetail(teamHomeId)).await(),
                TeamResponse::class.java
            )

            val teamAway = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetail(teamAwayId)).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.showEventDetail(data.events, teamHome.teams, teamAway.teams)
        }
    }

}