package com.tinashe.explorer.di

import com.tinashe.explorer.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindHomeActivity(): HomeActivity


}