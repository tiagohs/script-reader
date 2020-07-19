package com.tiagohs.entities.home

import com.tiagohs.entities.Script
import com.tiagohs.helpers.enums.HomeTypeEnum

class ListDefaultCell(
    var title: Int,
    var list: List<Script> = emptyList()
): HomeCell(HomeTypeEnum.LIST_DEFAULT)