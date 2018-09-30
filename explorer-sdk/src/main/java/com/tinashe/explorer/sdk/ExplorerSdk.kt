package com.tinashe.explorer.sdk

import android.content.Context
import com.tinashe.explorer.sdk.data.repository.ExplorerRepository
import com.tinashe.explorer.sdk.data.repository.ExplorerRepositoryImpl

/**
 * Entry point for communicating with the SDK
 */
object ExplorerSdk {

    private var repository: ExplorerRepository? = null

    fun getRepository(context: Context): ExplorerRepository {
        if (repository == null) {

            repository = ExplorerRepositoryImpl(context)
        }

        return repository as ExplorerRepository
    }
}