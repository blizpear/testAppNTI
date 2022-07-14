package com.blizpear.testAppNTI.features.menu.ui.viewholders

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blizpear.testAppNTI.features.menu.databinding.MenuSectionBinding
import com.blizpear.testAppNTI.features.menu.ui.adapters.MenuSectionAdapter
import com.blizpear.testAppNTI.features.menu.ui.model.MenuUIList

class MenuSectionHolder(
	private val binding: MenuSectionBinding,
	private val selectionAdapter: MenuSectionAdapter
) : RecyclerView.ViewHolder(binding.root) {

	companion object {

		fun from(
			parent: ViewGroup,
			onMenuClickAction: (menuId: String) -> Unit,
			loadImageAction: (view: ImageView, url: String?) -> Unit
		): MenuSectionHolder {
			val inflater = LayoutInflater.from(parent.context)
			val binding = MenuSectionBinding.inflate(inflater, parent, false)

			binding.menuRecycler.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
			val selectionAdapter = MenuSectionAdapter(loadImageAction, onMenuClickAction)
			binding.menuRecycler.adapter = selectionAdapter

			return MenuSectionHolder(binding, selectionAdapter)
		}
	}

	fun bind(menuUIList: MenuUIList) {
		menuUIList.menuUIList.forEach {
			if (it.isSelected) {
				binding.sectionText.text = it.menu.name
			}
		}
		selectionAdapter.submitList(menuUIList.menuUIList)
		binding.menuRecycler.restoreState(menuUIList.menuState)
	}

	private fun RecyclerView.restoreState(parcelable: Parcelable?) {
		if (parcelable == null) return
		layoutManager?.onRestoreInstanceState(parcelable)
	}

	fun onDetachedFromWindow(onMenuStateChangeAction: (menuState: Parcelable?) -> Unit) {
		onMenuStateChangeAction(binding.menuRecycler.layoutManager?.onSaveInstanceState())
	}
}