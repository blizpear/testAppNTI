package com.blizpear.testAppNTI.features.menu.data.repository

import com.blizpear.testAppNTI.features.menu.data.datasource.MenuDatasource
import com.blizpear.testAppNTI.features.menu.data.mappers.toEntitiesList
import com.blizpear.testAppNTI.features.menu.domain.entities.Menu
import com.blizpear.testAppNTI.features.menu.domain.entities.SubMenu
import com.blizpear.testAppNTI.features.menu.domain.repository.MenuRepository

class MenuRepositoryImpl(
	private val datasource: MenuDatasource
) : MenuRepository {

	override suspend fun getMenuList(): List<Menu> =
		datasource.getMenuList().toEntitiesList()

	override suspend fun getSubMenuList(menuID: String): List<SubMenu> =
		datasource.getSubMenuList(menuID).toEntitiesList()
}