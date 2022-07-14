package com.blizpear.testAppNTI.shared.network.di

import com.blizpear.testAppNTI.shared.network.RetrofitProvider
import org.koin.dsl.module

val networkModule = module {

	single { RetrofitProvider.retrofit }
}