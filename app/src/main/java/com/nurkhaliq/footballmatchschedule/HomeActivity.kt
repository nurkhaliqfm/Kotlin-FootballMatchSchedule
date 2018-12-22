package com.nurkhaliq.footballmatchschedule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.nurkhaliq.footballmatchschedule.R.id.*
import com.nurkhaliq.footballmatchschedule.favorite.FavoritesEventFragment
import com.nurkhaliq.footballmatchschedule.nextEvent.NextEventFragment
import com.nurkhaliq.footballmatchschedule.pastEvent.PastEventFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Football Match Schedule"

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                pastEvent -> {
                    loadPastEventFragment(savedInstanceState)
                }
                favorites -> {
                    loadFavoritesEventFragment(savedInstanceState)
                }
                nextEvent -> {
                    loadNextEventFragment(savedInstanceState)
                }


            }
            true
        }
        bottom_navigation.selectedItemId = pastEvent
    }

    private fun loadNextEventFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    NextEventFragment(), NextEventFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun loadFavoritesEventFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    FavoritesEventFragment(), FavoritesEventFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun loadPastEventFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    PastEventFragment(), PastEventFragment::class.java.simpleName
                )
                .commit()
        }
    }
}