package com.nurkhaliq.footballmatchschedule.nextEvent


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import com.nurkhaliq.footballmatchschedule.R
import com.nurkhaliq.footballmatchschedule.api.ApiRepository
import com.nurkhaliq.footballmatchschedule.detail.DetailActivity
import com.nurkhaliq.footballmatchschedule.model.Event
import com.nurkhaliq.footballmatchschedule.util.invisible
import com.nurkhaliq.footballmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class NextEventFragment : Fragment(), AnkoComponent<Context>, EventNextView {
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var eventsAdapter: EventsAdapterNext
    private lateinit var presenter: EventNextPresenter
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        eventsAdapter = EventsAdapterNext(events) {
            context?.startActivity<DetailActivity>(
                "idEvent" to "${it.idEvent}",
                "idHomeTeam" to "${it.idHomeTeam}", "idAwayTeam" to "${it.idAwayTeam}"
            )
        }
        listEvent.adapter = eventsAdapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventNextPresenter(this, request, gson)
        presenter.getEventList("4328")

        swipeRefresh.onRefresh {
            presenter.getEventList("4328")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventList(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        eventsAdapter.notifyDataSetChanged()
    }


}
