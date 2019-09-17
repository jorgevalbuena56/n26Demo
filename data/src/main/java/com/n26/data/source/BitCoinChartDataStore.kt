package com.n26.data.source

import com.n26.data.render.BitCoinChart
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to BitCoinChart.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface BitCoinChartDataStore {
    fun clearBitCoinChart(): Completable
    fun saveBitCoinChart(bitCoinChart: BitCoinChart): Completable
    fun getBitCoinChart(): Flowable<BitCoinChart>
    fun isCached(): Single<Boolean>
    fun setLastCacheTime(lastCache: Long)
    fun isExpired(): Boolean
}