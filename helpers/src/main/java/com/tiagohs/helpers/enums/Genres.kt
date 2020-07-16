package com.tiagohs.helpers.enums

import com.tiagohs.helpers.R

enum class Genres(val slug: String, val icon: Int, val title: Int) {
    ACTION( "action", R.drawable.ic_genre_action, R.string.genre_action),
    ADVENTURE( "adventure", 0, R.string.genre_adventure),
    ANIMATION( "animation", R.drawable.ic_genre_animation, R.string.genre_animation),
    BIOGRAPHY( "biography", 0, R.string.genre_biography),
    COMEDY( "comedy", R.drawable.ic_genre_comedy, R.string.genre_comedy),
    CRIME( "crime", 0, R.string.genre_crime),
    DRAMA( "drama", 0, R.string.genre_drama),
    FANTASY( "fantasy", 0, R.string.genre_fantasy),
    HORROR( "horror", 0, R.string.genre_horror),
    MUSICAL( "musical", 0, R.string.genre_musical),
    ROMANCE( "romance", 0, R.string.genre_romance),
    SCIENCE_FICTION( "science-fiction", 0, R.string.genre_science_fiction),
    THRILLER( "thriller", 0, R.string.genre_thriller),
    WAR( "war", 0, R.string.genre_war),
    WESTERN( "western", 0, R.string.genre_western)
}