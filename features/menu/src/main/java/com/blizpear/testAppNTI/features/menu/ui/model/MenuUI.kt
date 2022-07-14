package com.blizpear.testAppNTI.features.menu.ui.model

import com.blizpear.testAppNTI.features.menu.domain.entities.Menu

data class MenuUI(
	val menu: Menu,
	var isSelected: Boolean = false
)