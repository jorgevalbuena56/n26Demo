package com.n26.cache

import com.n26.cache.db.BitCoinChartDatabase
import com.n26.cache.mapper.BitCoinChartEntityMapper
import com.n26.cache.model.BitCoinChartCached
import com.n26.cache.model.BitCoinPriceCached
import com.n26.data.render.BitCoinChart
import com.n26.data.source.BitCoinChartDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class BitCoinChartImpl constructor(private val bitCoinChartDatabase: BitCoinChartDatabase,
                                   private val bitCoinChartEntityMapper: BitCoinChartEntityMapper,
                                   private val preferencesManager: PreferencesManager
):
    BitCoinChartDataStore {

    private val EXPIRATION_TIME = (60 * 5 * 1000).toLong() //five minutes

    /**
     * Remove all the data from all the tables in the database.
     */
    override fun clearBitCoinChart(): Completable {
        return Completable.defer {
            bitCoinChartDatabase.bitCoinchartCachedDao().clearBitCoinChart()
            Completable.complete()
        }
    }

    /**
     * Save the details of [BitCoinChart] to the database.
     */
    override fun saveBitCoinChart(bitCoinChart: BitCoinChart): Completable {
        return Completable.defer {
           val id = bitCoinChartDatabase.bitCoinchartCachedDao().insertBitCoinChart(
                bitCoinChartEntityMapper.mapToCached(bitCoinChart))
            bitCoinChartDatabase.bitCoinchartCachedDao().insertBitCoinPrices(
                bitCoinChart.values.map { BitCoinPriceCached(bitCoinChartId = id, x = it.x, y = it.y) })
            setLastCacheTime(System.currentTimeMillis())
            Completable.complete()
        }
    }

    /**
     * Retrieve the details of [BitCoinChart] from the database.
     */
    override fun getBitCoinChart(): Flowable<BitCoinChart> {
        return Flowable.defer {
            val bitCoinChart = bitCoinChartDatabase.bitCoinchartCachedDao().getBitCoinChart()
            return@defer if (bitCoinChart != null) {
                bitCoinChart.values =
                    bitCoinChartDatabase.bitCoinchartCachedDao().getBitCoinPrices(bitCoinChart.id)
                Flowable.just(bitCoinChart)
            }else {
                Flowable.just(BitCoinChartCached())
            }
        }.map {
            bitCoinChartEntityMapper.mapFromCached(it)
        }
    }

    /**
     * Check whether there are instances of [BitCoinChartCached] stored in the cache.
     */
    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(bitCoinChartDatabase.bitCoinchartCachedDao().getBitCoinChart() != null)
        }
    }

    /**
     * Set a point in time at when the cache was last updated.
     */
    override fun setLastCacheTime(lastCache: Long) {
        preferencesManager.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time.
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesManager.lastCacheTime
    }
}