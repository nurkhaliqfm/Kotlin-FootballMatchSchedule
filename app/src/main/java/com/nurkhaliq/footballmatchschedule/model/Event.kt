package com.nurkhaliq.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("dateEvent")
    var dateEvent: String? = null,
    @SerializedName("idEvent")
    var idEvent: Int = 0,
    @SerializedName("idHomeTeam")
    var idHomeTeam: Int = 0,
    @SerializedName("idAwayTeam")
    var idAwayTeam: Int = 0,
    @SerializedName("strHomeTeam")
    var strHomeTeam: String? = null,
    @SerializedName("strAwayTeam")
    var strAwayTeam: String? = null,
    @SerializedName("intHomeScore")
    var intHomeScore: String? = null,
    @SerializedName("intAwayScore")
    var intAwayScore: String? = null,
    @SerializedName("strHomeGoalDetails")
    var strHomeGoalDetails: String? = null,
    @SerializedName("strAwayGoalDetails")
    var strAwayGoalDetails: String? = null,
    @SerializedName("intHomeShots")
    var intHomeShots: String? = null,
    @SerializedName("strHomeLineupGoalkeeper")
    var strHomeLineupGoalkeeper: String? = null,
    @SerializedName("strHomeLineupDefense")
    var strHomeLineupDefense: String? = null,
    @SerializedName("strHomeLineupMidfield")
    var strHomeLineupMidfield: String? = null,
    @SerializedName("strHomeLineupForward")
    var strHomeLineupForward: String? = null,
    @SerializedName("intAwayShots")
    var intAwayShots: String? = null,
    @SerializedName("strAwayLineupGoalkeeper")
    var strAwayLineupGoalkeeper: String? = null,
    @SerializedName("strAwayLineupDefense")
    var strAwayLineupDefense: String? = null,
    @SerializedName("strAwayLineupMidfield")
    var strAwayLineupMidfield: String? = null,
    @SerializedName("strAwayLineupForward")
    var strAwayLineupForward: String? = null

)