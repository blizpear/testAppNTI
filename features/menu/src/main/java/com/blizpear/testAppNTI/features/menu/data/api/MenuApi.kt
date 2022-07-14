package com.blizpear.testAppNTI.features.menu.data.api

import com.blizpear.testAppNTI.features.menu.data.models.MenuInfoModel
import com.blizpear.testAppNTI.features.menu.data.models.SubMenuInfoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MenuApi {

	@GET("getMenu.php")
	suspend fun getMenu(): MenuInfoModel

	@GET("getSubMenu.php")
	suspend fun getSubMenu(@Query("menuID") menuID: String): SubMenuInfoModel
}