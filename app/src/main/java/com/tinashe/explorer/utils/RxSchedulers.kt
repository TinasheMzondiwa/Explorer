package com.tinashe.explorer.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

data class RxSchedulers(val main: Scheduler = AndroidSchedulers.mainThread())