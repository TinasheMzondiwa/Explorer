package com.tinashe.explorer.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.tinashe.explorer.ui.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelBuilder {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFractory(factory: ViewModelFactory): ViewModelProvider.Factory
}