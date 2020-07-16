package com.tiagohs.entities.home

import com.tiagohs.helpers.enums.Genres
import com.tiagohs.helpers.enums.HomeType

class GenreCell(
    var genres: List<Genres> = Genres.values().toList()
) : HomeCell(HomeType.GENRES)