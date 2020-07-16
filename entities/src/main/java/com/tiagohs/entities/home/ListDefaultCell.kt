package com.tiagohs.entities.home

import com.tiagohs.entities.Script
import com.tiagohs.helpers.enums.HomeType

class ListDefaultCell(
    var title: Int,
    var list: List<Script> = emptyList()
): HomeCell(HomeType.LIST_DEFAULT)