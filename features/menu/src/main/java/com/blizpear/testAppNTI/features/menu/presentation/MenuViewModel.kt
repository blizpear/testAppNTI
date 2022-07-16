package com.blizpear.testAppNTI.features.menu.presentation

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blizpear.testAppNTI.features.menu.domain.entities.SubMenu
import com.blizpear.testAppNTI.features.menu.domain.usecases.GetMenuUseCase
import com.blizpear.testAppNTI.features.menu.domain.usecases.GetSubMenuUseCase
import com.blizpear.testAppNTI.features.menu.ui.model.MenuUI
import com.blizpear.testAppNTI.features.menu.ui.model.MenuUIList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuViewModel(
	private val getMenuUseCase: GetMenuUseCase,
	private val getSubMenuUseCase: GetSubMenuUseCase
) : ViewModel() {

	private val _state = MutableStateFlow<MenuState>(MenuState.Initialize)
	val state = _state.asStateFlow()

	private var selectedMenuID: String = "21"

	private lateinit var menuUIList: MenuUIList

	init {
		_state.value = MenuState.Loading
		getMenuAndSubMenuLists()
	}

	private fun getMenuAndSubMenuLists(filter: String = selectedMenuID) {
		viewModelScope.launch {
			try {
				_state.value = MenuState.Loading
				menuUIList = MenuUIList(
					menuUIList = getMenuUseCase().map {
						MenuUI(menu = it, isSelected = filter == it.menuID)
					}
				)

				val subMenu = getSubMenuDroppedLast(filter)

				_state.value = MenuState.Content(menuUIList, subMenu)
			} catch (e: Exception) {
				Log.d("viewmodel", e.message!!)
				_state.value = MenuState.Error
			}
		}
	}

	private fun getSubMenu(filter: String) {
		viewModelScope.launch {
			try {
				val subMenu = getSubMenuDroppedLast(filter)
				_state.value = MenuState.Content(menuUIList, subMenu)
			} catch (e: Exception) {
				Log.d("viewmodel", e.message!!)
				_state.value = MenuState.Error
			}

		}
	}

	private suspend fun getSubMenuDroppedLast(filter: String): List<SubMenu> =
		getSubMenuUseCase(filter).map {
			it.price = it.price.dropLast(3).plus(" ₽")
			it
		}

	fun onMenuClicked(menuId: String) {
		viewModelScope.launch {
			selectedMenuID = if (menuId == selectedMenuID)
				menuId
			else {
				menuUIList = menuUIList.copy(
					menuUIList = menuUIList.menuUIList.map {
						it.copy(isSelected = menuId == it.menu.menuID)
					},
					menuState = menuUIList.menuState
				)
				getSubMenu(menuId)
				menuId
			}
		}
	}

	fun onMenuStateChange(state: Parcelable?) {
		menuUIList.menuState = state
	}

	fun clickOnErrorButton() {
		getMenuAndSubMenuLists(menuUIList.menuUIList.first().menu.menuID)
	}
}