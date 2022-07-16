package com.blizpear.testAppNTI.features.menu.ui.adapters

import android.os.Parcelable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blizpear.testAppNTI.features.menu.domain.entities.SubMenu
import com.blizpear.testAppNTI.features.menu.ui.model.ItemViewType
import com.blizpear.testAppNTI.features.menu.ui.model.MenuUIList
import com.blizpear.testAppNTI.features.menu.ui.model.MenuWithSubmenuModel
import com.blizpear.testAppNTI.features.menu.ui.viewholders.MenuSectionHolder
import com.blizpear.testAppNTI.features.menu.ui.viewholders.SubmenuHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuWithSubmenuAdapter(
	private val onMenuClickedAction: (menuId: String) -> Unit,
	private val loadImageAction: (view: ImageView, url: String?) -> Unit,
	private val onMenuStateChangeAction: (menuState: Parcelable?) -> Unit
) : ListAdapter<MenuWithSubmenuModel, RecyclerView.ViewHolder>(MenuDuffCallback) {

	private val coroutineScope = CoroutineScope(Job() + Dispatchers.Default)

	override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
		super.onViewDetachedFromWindow(holder)
		if (holder is MenuSectionHolder) {
			holder.onDetachedFromWindow(onMenuStateChangeAction)
		}
	}

	override fun getItemViewType(position: Int): Int {
		return getItem(position).viewType
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		when (viewType) {
			ItemViewType.MENU.type    ->
				MenuSectionHolder.from(
					parent,
					onMenuClickedAction,
					loadImageAction
				)

			ItemViewType.SUBMENU.type ->
				SubmenuHolder.from(parent)

			else                      -> throw IllegalStateException("Unresolved type of viewHolder")
		}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (holder) {
			is MenuSectionHolder -> holder.bind(
				(getItem(position) as MenuWithSubmenuModel.Menu).menuUIList,

				)

			is SubmenuHolder     -> holder.bind(
				(getItem(position) as MenuWithSubmenuModel.Submenu).subMenu,
				loadImageAction
			)
		}
	}

	fun submitData(
		menuUIList: MenuUIList,
		submenu: List<SubMenu>
	) {
		coroutineScope.launch {
			val newList = mutableListOf<MenuWithSubmenuModel>().apply {
				add(MenuWithSubmenuModel.Menu(menuUIList))
				addAll(submenu.map { MenuWithSubmenuModel.Submenu(it) })
			}
			withContext(Dispatchers.Main) {
				submitList(newList)
			}
		}
	}

	override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
		coroutineScope.cancel()
		super.onDetachedFromRecyclerView(recyclerView)
	}
}

object MenuDuffCallback : DiffUtil.ItemCallback<MenuWithSubmenuModel>() {

	override fun areItemsTheSame(
		oldItem: MenuWithSubmenuModel,
		newItem: MenuWithSubmenuModel
	): Boolean =
		if (oldItem is MenuWithSubmenuModel.Menu && newItem is MenuWithSubmenuModel.Menu) {
			oldItem.menuUIList.menuUIList.first().menu.menuID == newItem.menuUIList.menuUIList.first().menu.menuID
		} else if (oldItem is MenuWithSubmenuModel.Submenu && newItem is MenuWithSubmenuModel.Submenu) {
			oldItem.subMenu.id == oldItem.subMenu.id
		} else false

	override fun areContentsTheSame(
		oldItem: MenuWithSubmenuModel,
		newItem: MenuWithSubmenuModel
	): Boolean =
		oldItem == newItem
}