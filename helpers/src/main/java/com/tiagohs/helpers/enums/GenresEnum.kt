package com.tiagohs.helpers.enums

import com.tiagohs.helpers.R

enum class GenresEnum(val url: String, val icon: Int, val title: Int) {
    ACTION( "scripts/category/action", R.drawable.ic_genre_action, R.string.genre_action),
    ADVENTURE( "scripts/category/adventure", R.drawable.ic_genre_adventure, R.string.genre_adventure),
    ANIMATION( "scripts/category/animation", R.drawable.ic_genre_animation, R.string.genre_animation),
    BIOGRAPHY( "scripts/category/biography", R.drawable.ic_genre_biography, R.string.genre_biography),
    COMEDY( "scripts/category/comedy", R.drawable.ic_genre_comedy, R.string.genre_comedy),
    CRIME( "scripts/category/crime", R.drawable.ic_genre_crime, R.string.genre_crime),
    DRAMA( "scripts/category/drama", R.drawable.ic_genre_drama, R.string.genre_drama),
    FANTASY( "scripts/category/fantasy", R.drawable.ic_genre_fantasy, R.string.genre_fantasy),
    HORROR( "scripts/category/horror", R.drawable.ic_genre_horror, R.string.genre_horror),
    MUSICAL( "scripts/category/musical", R.drawable.ic_genre_musical, R.string.genre_musical),
    ROMANCE( "scripts/category/romance", R.drawable.ic_genre_romance, R.string.genre_romance),
    SCIENCE_FICTION( "scripts/category/science-fiction", R.drawable.ic_genre_sci_fi, R.string.genre_science_fiction),
    THRILLER( "scripts/category/thriller", R.drawable.ic_genre_thriller, R.string.genre_thriller),
    WAR( "scripts/category/war", R.drawable.ic_genre_war, R.string.genre_war),
    WESTERN( "scripts/category/western", R.drawable.ic_genre_western, R.string.genre_western)
}