package com.nurkhaliq.footballmatchschedule.detail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.nurkhaliq.footballmatchschedule.R
import com.nurkhaliq.footballmatchschedule.R.id.add_to_favorites
import com.nurkhaliq.footballmatchschedule.api.ApiRepository
import com.nurkhaliq.footballmatchschedule.database
import com.nurkhaliq.footballmatchschedule.favorite.Favorite
import com.nurkhaliq.footballmatchschedule.model.Event
import com.nurkhaliq.footballmatchschedule.model.Team
import com.nurkhaliq.footballmatchschedule.util.invisible
import com.nurkhaliq.footballmatchschedule.util.visible
import com.squareup.picasso.Picasso
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh

class DetailActivity : AppCompatActivity(), EventDetailView {

    private lateinit var events: Event
    private lateinit var teamsHome: Team
    private lateinit var teamsAway: Team
    private lateinit var presenter: EventDetailPresenter
    private lateinit var idEvent: String
    private lateinit var idHomeTeam: String
    private lateinit var idAwayTeam: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Detail Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idEvent = intent.getStringExtra("idEvent")
        idHomeTeam = intent.getStringExtra("idHomeTeam")
        idAwayTeam = intent.getStringExtra("idAwayTeam")

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = EventDetailPresenter(this, request, gson)
        presenter.getEventDetail(idEvent, idHomeTeam, idAwayTeam)

        swipeRefresh.onRefresh {
            presenter.getEventDetail(idEvent, idHomeTeam, idAwayTeam)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if (this::events.isInitialized) {
            if (item?.itemId == android.R.id.home) {
                finish()
            }
            if (item?.itemId == add_to_favorites) {
                if (isFavorite)
                    removeFromFavorite()
                else
                    addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
            }
        }
        return true
    }


    private fun addToFavorite() {
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE_TEAM,
                    Favorite.ID_EVENT to events.idEvent,
                    Favorite.ID_HOME to events.idHomeTeam,
                    Favorite.ID_AWAY to events.idAwayTeam,
                    Favorite.DATE_EVENT to events.dateEvent,
                    Favorite.INT_AWAY_SCORE to events.intAwayScore,
                    Favorite.INT_HOME_SCORE to events.intHomeScore,
                    Favorite.STR_HOME_TEAM to events.strHomeTeam,
                    Favorite.STR_AWAY_TEAM to events.strAwayTeam
                )
            }
            swipeRefresh.snackbar("Added To Favorite").show()
        } catch (e: SQLiteConstraintException) {
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE_TEAM, "(${Favorite.ID_EVENT} = {idEvent})", "idEvent" to idEvent)
            }
            swipeRefresh.snackbar("Removed From Favorite").show()
        } catch (e: SQLiteConstraintException) {
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_favorites)
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE_TEAM)
                .whereArgs("(${Favorite.ID_EVENT} = {idEvent})", "idEvent" to idEvent)

            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        val progressBar = findViewById<ProgressBar>(R.id.progress_circular)
        progressBar.visible()
    }

    override fun hideLoading() {
        val progressBar = findViewById<ProgressBar>(R.id.progress_circular)
        progressBar.invisible()
    }

    override fun showEventDetail(data: List<Event>, teamHome: List<Team>, teamAway: List<Team>) {
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        teamsHome = Team(teamHome[0].strTeamBadge)
        teamsAway = Team(teamAway[0].strTeamBadge)
        events = Event(
            data[0].dateEvent,
            data[0].idEvent,
            data[0].idHomeTeam,
            data[0].idAwayTeam,
            data[0].strHomeTeam,
            data[0].strAwayTeam,
            data[0].intHomeScore,
            data[0].intAwayScore,
            data[0].strHomeGoalDetails,
            data[0].strAwayGoalDetails,
            data[0].intHomeShots,
            data[0].strHomeLineupGoalkeeper,
            data[0].strHomeLineupDefense,
            data[0].strHomeLineupMidfield,
            data[0].strHomeLineupForward,
            data[0].intAwayShots,
            data[0].strAwayLineupGoalkeeper,
            data[0].strAwayLineupDefense,
            data[0].strAwayLineupMidfield,
            data[0].strAwayLineupForward
        )

        swipeRefresh.isRefreshing = false

        val dateEvent = findViewById<TextView>(R.id.event_date)
        val strHomeTeam = findViewById<TextView>(R.id.homeName)
        val strAwayTeam = findViewById<TextView>(R.id.awayName)
        val intHomeScore = findViewById<TextView>(R.id.home_score)
        val intAwyScore = findViewById<TextView>(R.id.away_score)
        val strHomeGoalDetails = findViewById<TextView>(R.id.goals_home)
        val strAwyGoalDetails = findViewById<TextView>(R.id.goals_away)
        val intHomeShots = findViewById<TextView>(R.id.hshoot)
        val intHAwayShots = findViewById<TextView>(R.id.ashoot)
        val strHomeLineupGoalkeeper = findViewById<TextView>(R.id.gk_Home)
        val strAwayLineupGoalkeeper = findViewById<TextView>(R.id.gk_Away)
        val strHomeLineupDefense = findViewById<TextView>(R.id.df_Home)
        val strAwayLineupDefense = findViewById<TextView>(R.id.df_Away)
        val strHomeLineupMidfield = findViewById<TextView>(R.id.mf_Home)
        val strAwayLineupMidfield = findViewById<TextView>(R.id.mf_Away)
        val strHomeLineupForward = findViewById<TextView>(R.id.f_Home)
        val strAwayLineupForward = findViewById<TextView>(R.id.f_Away)
        val strTeamBadgeHome = findViewById<ImageView>(R.id.homeLogo)
        val strTeamBadgeAway = findViewById<ImageView>(R.id.awayLogo)

        dateEvent.text = data[0].dateEvent
        strHomeTeam.text = data[0].strHomeTeam
        strAwayTeam.text = data[0].strAwayTeam
        intHomeScore.text = data[0].intHomeScore
        intAwyScore.text = data[0].intAwayScore
        strHomeGoalDetails.text = data[0].strHomeGoalDetails
        strAwyGoalDetails.text = data[0].strAwayGoalDetails
        intHomeShots.text = data[0].intHomeShots
        intHAwayShots.text = data[0].intAwayShots
        strHomeLineupGoalkeeper.text = data[0].strHomeLineupGoalkeeper
        strAwayLineupGoalkeeper.text = data[0].strAwayLineupGoalkeeper
        strHomeLineupDefense.text = data[0].strHomeLineupDefense
        strAwayLineupDefense.text = data[0].strAwayLineupDefense
        strHomeLineupMidfield.text = data[0].strHomeLineupMidfield
        strAwayLineupMidfield.text = data[0].strAwayLineupMidfield
        strHomeLineupForward.text = data[0].strHomeLineupForward
        strAwayLineupForward.text = data[0].strAwayLineupForward
        Picasso.get().load(teamHome[0].strTeamBadge).into(strTeamBadgeHome)
        Picasso.get().load(teamAway[0].strTeamBadge).into(strTeamBadgeAway)

    }

}
