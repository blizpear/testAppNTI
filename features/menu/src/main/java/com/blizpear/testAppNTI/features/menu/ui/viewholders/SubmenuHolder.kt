package com.blizpear.testAppNTI.features.menu.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.blizpear.testAppNTI.features.menu.databinding.SubmenuItemBinding
import com.blizpear.testAppNTI.features.menu.domain.entities.SubMenu

class SubmenuHolder(
	private val binding: SubmenuItemBinding
) : RecyclerView.ViewHolder(binding.root) {

	companion object {

		fun from(parent: ViewGroup): SubmenuHolder {
			val inflater = LayoutInflater.from(parent.context)
			val binding = SubmenuItemBinding.inflate(inflater, parent, false)
			return SubmenuHolder(binding)
		}
	}

	fun bind(
		subMenu: SubMenu,
		loadImageAction: (view: ImageView, url: String?) -> Unit
	) {
		with(binding) {
			foodName.text = subMenu.name
			foodContent.text = subMenu.content
			foodPrice.text = subMenu.price
			if (subMenu.weight.isNullOrEmpty()) {
				slash.isVisible = false
				foodWeight.isVisible = false
			} else {
				slash.isVisible = true
				foodWeight.text = subMenu.weight
				foodWeight.isVisible = true
			}
			loadImageAction(foodImage, subMenu.image)
			foodSpicy.isVisible = !subMenu.spicy.isNullOrEmpty()
		}
	}
}