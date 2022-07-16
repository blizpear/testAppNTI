package com.blizpear.testAppNTI.features.menu.ui.viewholders

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blizpear.testAppNTI.features.menu.R
import com.blizpear.testAppNTI.features.menu.databinding.MenuItemBinding
import com.blizpear.testAppNTI.features.menu.ui.model.MenuUI

class MenuHolder(
	private val binding: MenuItemBinding
) : RecyclerView.ViewHolder(binding.root) {

	companion object {

		fun from(parent: ViewGroup): MenuHolder {
			val inflater = LayoutInflater.from(parent.context)
			val binding = MenuItemBinding.inflate(inflater, parent, false)
			return MenuHolder(binding)
		}
	}

	fun bind(
		menuUI: MenuUI,
		onClickAction: (menuID: String) -> Unit,
		loadImageAction: (view: ImageView, url: String?) -> Unit
	) {
		binding.menuName.text = menuUI.menu.name
		binding.menuCount.text = binding.root.context.getString(R.string.products, menuUI.menu.subMenuCount)
		loadImageAction(binding.menuImage, menuUI.menu.image)

		binding.root.setOnClickListener {
			onClickAction(menuUI.menu.menuID)
		}

		binding.menuElement.setCardBackgroundColor(
			if (menuUI.isSelected)
				ContextCompat.getColor(binding.root.context, R.color.blue)
			else
				getColorFromAttr(
					binding.root.context,
					com.google.android.material.R.attr.colorOnPrimary
				)
		)
	}

	private fun getColorFromAttr(context: Context, @AttrRes attr: Int): Int = TypedValue().run {
		context.theme.resolveAttribute(attr, this, true)
		data
	}
}