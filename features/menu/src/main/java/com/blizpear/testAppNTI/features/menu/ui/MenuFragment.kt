package com.blizpear.testAppNTI.features.menu.ui

import SubMenuDecorator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.blizpear.testAppNTI.features.menu.R
import com.blizpear.testAppNTI.features.menu.databinding.MenuFragmentBinding
import com.blizpear.testAppNTI.features.menu.presentation.MenuState
import com.blizpear.testAppNTI.features.menu.presentation.MenuViewModel
import com.blizpear.testAppNTI.features.menu.ui.adapters.MenuWithSubmenuAdapter
import com.blizpear.testAppNTI.features.menu.ui.model.ItemViewType
import com.blizpear.testAppNTI.shared.ui.fragment.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment :
	BaseFragment<MenuFragmentBinding>(R.layout.menu_fragment) {

	companion object {

		fun getInstance() = MenuFragment()

		const val ONE_ELEMENT_IN_ROW = 2
		const val TWO_ELEMENT_IN_ROW = 1
	}

	private lateinit var subMenuAdapterMenuWith: MenuWithSubmenuAdapter

	private val viewModel: MenuViewModel by viewModel()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setObservers()
		setAdapter()
		setListeners()
	}

	private fun setObservers() {
		viewLifecycleOwner.lifecycleScope.launchWhenStarted {
			viewModel.state.collect {
				handleState(it)
			}
		}
	}

	private fun handleState(state: MenuState) {
		when (state) {
			is MenuState.Initialize,
			MenuState.Loading    -> renderLoading()

			is MenuState.Error   -> renderError()

			is MenuState.Content -> renderContent(state)
		}
	}

	private fun renderLoading() {
		with(binding) {
			error.isVisible = false
			content.isVisible = false

			progressBar.isVisible = true
		}
	}

	private fun renderError() {
		with(binding) {
			content.isVisible = false
			progressBar.isVisible = false

			error.isVisible = true
		}
	}

	private fun renderContent(state: MenuState.Content) {
		with(binding) {
			error.isVisible = false
			progressBar.isVisible = false

			content.isVisible = true

			subMenuAdapterMenuWith.submitData(state.menu, state.subMenu)
		}
	}

	private fun setAdapter() {
		subMenuAdapterMenuWith = MenuWithSubmenuAdapter(::onMenuClicked, ::loadImageForItem, viewModel::onMenuStateChange)
		binding.content.adapter = subMenuAdapterMenuWith

		val manager = GridLayoutManager(requireContext(), 2)
		manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
			override fun getSpanSize(position: Int): Int =
				when (val viewType = subMenuAdapterMenuWith.getItemViewType(position)) {
					ItemViewType.MENU.type    -> ONE_ELEMENT_IN_ROW

					ItemViewType.SUBMENU.type -> TWO_ELEMENT_IN_ROW

					else                      -> throw IllegalStateException("Unknown viewType $viewType")
				}
		}

		binding.content.layoutManager = manager
		binding.content.addItemDecoration(SubMenuDecorator(resources.getDimensionPixelOffset(R.dimen.small)))
	}

	private fun onMenuClicked(menuId: String) {
		viewModel.onMenuClicked(menuId)
	}

	private fun loadImageForItem(view: ImageView, url: String?) {
		if (url != null) {
			Glide.with(this)
				.load("https://vkus-sovet.ru$url")
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(com.blizpear.testAppNTI.shared.ui.R.drawable.ic_placeholder)
				.into(view)
		} else {
			view.setImageDrawable(
				ContextCompat.getDrawable(
					requireContext(),
					com.blizpear.testAppNTI.shared.ui.R.drawable.ic_placeholder
				)
			)
		}
	}

	private fun setListeners() {
		binding.retryButton.setOnClickListener {
			viewModel.clickOnErrorButton()
		}
	}

	override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): MenuFragmentBinding =
		MenuFragmentBinding.inflate(inflater, container, false)
}