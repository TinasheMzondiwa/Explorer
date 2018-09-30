package com.tinashe.explorer.sdk.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

internal data class RxSchedulers(
        val database: Scheduler = Schedulers.single(),
        val network: Scheduler = Schedulers.io())