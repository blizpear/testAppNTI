package com.blizpear.testAppNTI.features.menu.domain.entities

data class SubMenu(
	val id: String,
	val image: String,
	val name: String,
	val content: String,
	var price: String,
	var weight: String?,
	val spicy: String?
)