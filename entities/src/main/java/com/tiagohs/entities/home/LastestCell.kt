package com.tiagohs.entities.home

import com.tiagohs.entities.Script
import com.tiagohs.helpers.enums.HomeType

class LastestCell(
    var list: List<Script> = emptyList()
): HomeCell(HomeType.LASTEST)