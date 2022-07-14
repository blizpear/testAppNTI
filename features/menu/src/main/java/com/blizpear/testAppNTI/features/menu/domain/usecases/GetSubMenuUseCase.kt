package com.blizpear.testAppNTI.features.menu.domain.usecases

import com.blizpear.testAppNTI.features.menu.domain.repository.MenuRepository

class GetSubMenuUseCase(
	private val repository: MenuRepository
) {

	suspend operator fun invoke(menuID: String) = repository.getSubMenuList(menuID)
}