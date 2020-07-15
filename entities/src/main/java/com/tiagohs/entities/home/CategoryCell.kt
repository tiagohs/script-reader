package com.tiagohs.entities.home

import com.tiagohs.helpers.enums.HomeType

class CategoryCell(
    var list: List<com.tiagohs.entities.Category> = emptyList()
): com.tiagohs.entities.home.HomeCell(HomeType.CATEGORIES)