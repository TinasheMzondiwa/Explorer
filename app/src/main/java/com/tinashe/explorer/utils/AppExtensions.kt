package com.tinashe.explorer.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tinashe.explorer.di.ViewModelFactory

inline fun <reified T : ViewModel> getViewModel(activity: FragmentActivity, factory: ViewModelFactory): T {
    return ViewModelProviders.of(activity, factory)[T::class.java]
}


fun inflateView(@LayoutRes layoutResId: Int, parent: ViewGroup, attachToRoot: Boolean): View =
        LayoutInflater.from(parent.context).inflate(layoutResId, parent, attachToRoot)

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}