package com.blizpear.myapplication

import android.app.Application
import com.blizpear.myapplication.di.globalNavigationModule
import com.blizpear.testAppNTI.shared.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@App)

			modules(
				globalNavigationModule,
				networkModule,

			)
		}

	}
}