package com.blizpear.testAppNTI.features.menu.data.datasource

import com.blizpear.testAppNTI.features.menu.data.models.MenuModel
import com.blizpear.testAppNTI.features.menu.data.models.SubMenuModel

interface MenuDatasource {

	suspend fun getMenuList(): List<MenuModel>

	suspend fun getSubMenuList(menuID: String): List<SubMenuModel>
}