package com.blizpear.testAppNTI.features.menu.data.mappers

import com.blizpear.testAppNTI.features.menu.data.models.MenuModel
import com.blizpear.testAppNTI.features.menu.domain.entities.Menu

fun MenuModel.toEntity() = Menu(
	menuID = menuID,
	image = image,
	name = name,
	subMenuCount = subMenuCount
)

fun List<MenuModel>.toEntitiesList() = map(MenuModel::toEntity)