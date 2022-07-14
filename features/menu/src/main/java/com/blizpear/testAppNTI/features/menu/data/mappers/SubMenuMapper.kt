package com.blizpear.testAppNTI.features.menu.data.mappers

import com.blizpear.testAppNTI.features.menu.data.models.SubMenuModel
import com.blizpear.testAppNTI.features.menu.domain.entities.SubMenu

fun SubMenuModel.toEntity() = SubMenu(
	id = id,
	image = image,
	name = name, content = content,
	price = price,
	weight = weight,
	spicy = spicy
)

fun List<SubMenuModel>.toEntitiesList() = map(SubMenuModel::toEntity)