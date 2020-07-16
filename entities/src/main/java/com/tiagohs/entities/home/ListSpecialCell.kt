package com.tiagohs.entities.home

import com.tiagohs.entities.Script
import com.tiagohs.helpers.enums.HomeSpecialList
import com.tiagohs.helpers.enums.HomeType

class ListSpecialCell(
    val title: Int,
    val subtitle: Int,
    val config: HomeSpecialList,
    val list: List<Script> = emptyList()
): HomeCell(HomeType.LIST_SPECIAL)