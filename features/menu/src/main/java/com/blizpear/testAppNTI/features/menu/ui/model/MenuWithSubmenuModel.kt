package com.blizpear.testAppNTI.features.menu.ui.model

import com.blizpear.testAppNTI.features.menu.domain.entities.SubMenu

enum class ItemViewType(val type: Int) {
	MENU(0),
	SUBMENU(1)
}

sealed class MenuWithSubmenuModel {

	abstract val viewType: Int

	data class Menu(val menuUIList: MenuUIList) : MenuWithSubmenuModel() {

		override val viewType: Int = ItemViewType.MENU.type
	}

	data class Submenu(val subMenu: SubMenu) : MenuWithSubmenuModel() {

		override val viewType: Int = ItemViewType.SUBMENU.type
	}
}