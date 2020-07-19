package com.tiagohs.entities.home

import com.tiagohs.helpers.enums.HomeTypeEnum


abstract class HomeCell(
    val type: HomeTypeEnum = HomeTypeEnum.UNKNOWN
)