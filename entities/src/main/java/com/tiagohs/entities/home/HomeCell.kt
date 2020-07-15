package com.tiagohs.entities.home

import com.tiagohs.helpers.enums.HomeType


abstract class HomeCell(
    val type: HomeType = HomeType.UNKNOWN
)