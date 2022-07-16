package com.blizpear.testAppNTI.features.menu.domain.repository

import com.blizpear.testAppNTI.features.menu.domain.entities.Menu
import com.blizpear.testAppNTI.features.menu.domain.entities.SubMenu

interface MenuRepository {

	suspend fun getMenuList(): List<Menu>

	suspend fun getSubMenuList(menuID: String): List<SubMenu>
}