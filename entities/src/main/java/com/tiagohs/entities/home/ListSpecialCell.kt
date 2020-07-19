package com.tiagohs.entities.home

import com.tiagohs.entities.Script
import com.tiagohs.helpers.enums.HomeSpecialListEnum
import com.tiagohs.helpers.enums.HomeTypeEnum

class ListSpecialCell(
    val title: Int,
    val subtitle: Int,
    val config: HomeSpecialListEnum,
    val list: List<Script> = emptyList()
): HomeCell(HomeTypeEnum.LIST_SPECIAL)