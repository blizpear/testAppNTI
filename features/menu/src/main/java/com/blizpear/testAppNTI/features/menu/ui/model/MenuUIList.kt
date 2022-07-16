package com.blizpear.testAppNTI.features.menu.ui.model

import android.os.Parcelable

data class MenuUIList(
	var menuUIList: List<MenuUI>,
	var menuState: Parcelable? = null
)