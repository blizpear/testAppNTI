package com.blizpear.testAppNTI.features.menu.data.models

import com.squareup.moshi.Json

data class SubMenuInfoModel(
	val status: Boolean,
	@Json(name = "menuList")
	val subMenuList: List<SubMenuModel>
)