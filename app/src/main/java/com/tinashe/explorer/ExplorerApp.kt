package com.tinashe.explorer

import android.app.Activity
import android.app.Application
import com.tinashe.explorer.di.DaggerExplorerAppComponent
import com.tinashe.explorer.sdk.ExplorerSdk
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class ExplorerApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()

        DaggerExplorerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

    }
}