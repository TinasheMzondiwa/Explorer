package com.tinashe.explorer.di

import com.tinashe.explorer.ExplorerApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(ExplorerAppModule::class),
    (AndroidInjectionModule::class),
    (ViewModelBuilder::class),
    (ActivityBuilder::class)])
interface ExplorerAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: ExplorerApp): Builder

        fun build(): ExplorerAppComponent
    }

    fun inject(app: ExplorerApp)
}