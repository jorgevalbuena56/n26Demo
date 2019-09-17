package com.n26.remote.service

import com.n26.remote.model.BitCoinChartModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BitCoinChartService {
    @GET("/charts/{operation}")
    fun getBitCoinChart(@Path("operation") chartType: String = "market-price",
                        @Query("timespan") timespan: String = "180days",
                        @Query("format") format: String = "json"): Flowable<BitCoinChartModel>
}