package com.blizpear.testAppNTI.features.menu.domain.usecases

import com.blizpear.testAppNTI.features.menu.domain.repository.MenuRepository

class GetMenuUseCase(
	private val repository: MenuRepository
) {

	suspend operator fun invoke() =
		repository.getMenuList()
}