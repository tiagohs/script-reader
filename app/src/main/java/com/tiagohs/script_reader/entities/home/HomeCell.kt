package com.tiagohs.script_reader.entities.home

import com.tiagohs.script_reader.helpers.enums.HomeType

abstract class HomeCell(
    val type: HomeType = HomeType.UNKNOWN
)