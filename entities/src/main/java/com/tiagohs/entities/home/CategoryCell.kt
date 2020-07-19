package com.tiagohs.entities.home

import com.tiagohs.entities.Category
import com.tiagohs.helpers.enums.HomeTypeEnum

class CategoryCell(
    var list: List<Category> = emptyList()
): HomeCell(HomeTypeEnum.CATEGORIES)