package com.n26.data.repository

import com.n26.data.render.BitCoinChart
import io.reactivex.Completable
import io.reactivex.Flowable

interface BitCoinChartRepository {
    fun clearBitCoinChart(): Completable
    fun saveBitCoinChart(bitCoinChart: BitCoinChart): Completable
    fun getBitCoinChart(): Flowable<BitCoinChart>
}