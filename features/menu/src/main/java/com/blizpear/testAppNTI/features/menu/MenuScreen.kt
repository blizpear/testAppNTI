package com.blizpear.testAppNTI.features.menu

import com.blizpear.testAppNTI.features.menu.ui.MenuFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getMenuScreen() = FragmentScreen { MenuFragment.getInstance() }