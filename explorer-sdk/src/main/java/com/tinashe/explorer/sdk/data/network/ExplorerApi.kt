package com.tinashe.explorer.sdk.data.network

import com.tinashe.explorer.sdk.data.model.response.CitiesResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

internal interface ExplorerApi {

    @GET("/v2/5b7e8bc03000005c0084c210")
    fun getAllCities(): Observable<Response<CitiesResponse>>
}