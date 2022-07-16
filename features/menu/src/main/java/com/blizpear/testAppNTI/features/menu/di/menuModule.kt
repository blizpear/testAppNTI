package com.blizpear.testAppNTI.features.menu.di

import com.blizpear.testAppNTI.features.menu.data.api.MenuApi
import com.blizpear.testAppNTI.features.menu.data.datasource.MenuDatasource
import com.blizpear.testAppNTI.features.menu.data.datasource.MenuDatasourceImpl
import com.blizpear.testAppNTI.features.menu.data.repository.MenuRepositoryImpl
import com.blizpear.testAppNTI.features.menu.domain.repository.MenuRepository
import com.blizpear.testAppNTI.features.menu.domain.usecases.GetMenuUseCase
import com.blizpear.testAppNTI.features.menu.domain.usecases.GetSubMenuUseCase
import com.blizpear.testAppNTI.features.menu.presentation.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val menuModule = module {

	factory<MenuApi> { get<Retrofit>().create(MenuApi::class.java) }

	factory<MenuDatasource> { MenuDatasourceImpl(api = get()) }

	factory<MenuRepository> { MenuRepositoryImpl(datasource = get()) }

	factory { GetMenuUseCase(repository = get()) }
	factory { GetSubMenuUseCase(repository = get()) }

	viewModel {
		MenuViewModel(
			getMenuUseCase = get(),
			getSubMenuUseCase = get()
		)
	}
}