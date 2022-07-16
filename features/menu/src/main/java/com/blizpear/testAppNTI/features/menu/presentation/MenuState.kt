package com.blizpear.testAppNTI.features.menu.presentation

import com.blizpear.testAppNTI.features.menu.domain.entities.SubMenu
import com.blizpear.testAppNTI.features.menu.ui.model.MenuUIList

sealed class MenuState {

	object Initialize : MenuState()

	object Loading : MenuState()

	object Error : MenuState()

	class Content(
		val menu: MenuUIList,
		val subMenu: List<SubMenu>
	) : MenuState()
}