package com.nurkhaliq.footballmatchschedule.pastEvent

import com.google.gson.Gson
import com.nurkhaliq.footballmatchschedule.TextContextProvider
import com.nurkhaliq.footballmatchschedule.api.ApiRepository
import com.nurkhaliq.footballmatchschedule.api.TheSportDBApi
import com.nurkhaliq.footballmatchschedule.model.Event
import com.nurkhaliq.footballmatchschedule.model.EventResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EventPastPresenterTest {

    @Test
    fun getEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val id = "4328"
        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getEvents(id)).await(),
                    EventResponse::class.java
                )
            ).thenReturn(response)

            presenter.getEventList(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(events)
            Mockito.verify(view).hideLoading()

        }
    }

    @Mock
    private lateinit var view: EventPastView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    private lateinit var presenter: EventPastPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = EventPastPresenter(view, apiRepository, gson, TextContextProvider())
    }

}