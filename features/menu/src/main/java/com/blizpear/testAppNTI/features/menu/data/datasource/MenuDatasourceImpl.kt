package com.blizpear.testAppNTI.features.menu.data.datasource

import com.blizpear.testAppNTI.features.menu.data.api.MenuApi
import com.blizpear.testAppNTI.features.menu.data.models.MenuModel
import com.blizpear.testAppNTI.features.menu.data.models.SubMenuModel

class MenuDatasourceImpl(
	private val api: MenuApi
) : MenuDatasource {

	override suspend fun getMenuList(): List<MenuModel> = api.getMenu().menuList

	override suspend fun getSubMenuList(menuID: String): List<SubMenuModel> = api.getSubMenu(menuID).subMenuList
}