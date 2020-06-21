package com.tiagohs.script_reader.entities.home

import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.helpers.enums.HomeType

class LastestCell(
    var list: List<Script> = emptyList()
): HomeCell(HomeType.LASTEST)