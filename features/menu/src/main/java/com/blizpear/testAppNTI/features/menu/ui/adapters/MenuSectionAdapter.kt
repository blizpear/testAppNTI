package com.blizpear.testAppNTI.features.menu.ui.adapters

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.blizpear.testAppNTI.features.menu.ui.model.MenuUI
import com.blizpear.testAppNTI.features.menu.ui.viewholders.MenuHolder

class MenuSectionAdapter(
	private val loadingImageAction: (view: ImageView, url: String?) -> Unit,
	private val onMenuClickAction: (menuId: String) -> Unit
) : ListAdapter<MenuUI, MenuHolder>(MenuDiffUtil()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder =
		MenuHolder.from(parent)

	override fun onBindViewHolder(holder: MenuHolder, position: Int) {
		holder.bind(getItem(position), onMenuClickAction, loadingImageAction)
	}

	private class MenuDiffUtil : DiffUtil.ItemCallback<MenuUI>() {

		override fun areItemsTheSame(oldItem: MenuUI, newItem: MenuUI): Boolean =
			oldItem.menu == newItem.menu

		override fun areContentsTheSame(oldItem: MenuUI, newItem: MenuUI): Boolean =
			oldItem == newItem
	}
}