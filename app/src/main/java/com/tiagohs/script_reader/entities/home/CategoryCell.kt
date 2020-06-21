package com.tiagohs.script_reader.entities.home

import com.tiagohs.script_reader.entities.Category
import com.tiagohs.script_reader.helpers.enums.HomeType

class CategoryCell(
    var list: List<Category> = emptyList()
): HomeCell(HomeType.CATEGORIES)