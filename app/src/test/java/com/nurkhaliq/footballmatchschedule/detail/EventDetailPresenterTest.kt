package com.nurkhaliq.footballmatchschedule.detail

import com.google.gson.Gson
import com.nurkhaliq.footballmatchschedule.TextContextProvider
import com.nurkhaliq.footballmatchschedule.api.ApiRepository
import com.nurkhaliq.footballmatchschedule.api.TheSportDBApi
import com.nurkhaliq.footballmatchschedule.model.Event
import com.nurkhaliq.footballmatchschedule.model.EventResponse
import com.nurkhaliq.footballmatchschedule.model.Team
import com.nurkhaliq.footballmatchschedule.model.TeamResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class EventDetailPresenterTest {

    @Test
    fun getEventDetail() {
        val events: MutableList<Event> = mutableListOf()
        val teams: MutableList<Team> = mutableListOf()
        val response = EventResponse(events)
        val responseTeam = TeamResponse(teams)
        val id = "4328"
        val teamHomeId = "133602"
        val teamAwayId = "133614"
        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getEventsNext(id)).await(),
                    EventResponse::class.java
                )
            ).thenReturn(response)

            `when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getTeamDetail(teamHomeId)).await(),
                    TeamResponse::class.java
                )
            ).thenReturn(responseTeam)

            `when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getTeamDetail(teamAwayId)).await(),
                    TeamResponse::class.java
                )
            ).thenReturn(responseTeam)

            presenter.getEventDetail(id, teamHomeId, teamAwayId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventDetail(events, teams, teams)
            Mockito.verify(view).hideLoading()

        }
    }

    @Mock
    private lateinit var view: EventDetailView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    private lateinit var presenter: EventDetailPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = EventDetailPresenter(view, apiRepository, gson, TextContextProvider())
    }
}