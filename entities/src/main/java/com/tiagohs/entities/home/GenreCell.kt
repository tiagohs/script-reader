package com.tiagohs.entities.home

import com.tiagohs.helpers.enums.GenresEnum
import com.tiagohs.helpers.enums.HomeTypeEnum

class GenreCell(
    var genres: List<GenresEnum> = GenresEnum.values().toList()
) : HomeCell(HomeTypeEnum.GENRES)