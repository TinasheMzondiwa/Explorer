package com.tinashe.explorer.sdk.utils

import android.content.Context
import android.net.ConnectivityManager

internal class NetworkHelper {

    fun hasConnection(context: Context): Boolean {
        val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnected
    }
}