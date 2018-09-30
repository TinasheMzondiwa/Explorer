package com.tinashe.explorer.di

import android.content.Context
import com.tinashe.explorer.ExplorerApp
import com.tinashe.explorer.sdk.ExplorerSdk
import com.tinashe.explorer.sdk.data.repository.ExplorerRepository
import com.tinashe.explorer.utils.RxSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExplorerAppModule {

    @Provides
    @Singleton
    fun provideContext(app: ExplorerApp): Context = app

    @Provides
    @Singleton
    fun provideRepository(context: Context): ExplorerRepository = ExplorerSdk.getRepository(context)

    @Provides
    @Singleton
    fun provideRxSchedulers(): RxSchedulers = RxSchedulers()
}