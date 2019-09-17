package com.n26.cache.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.n26.cache.db.Constant
import com.n26.cache.model.BitCoinChartCached
import com.n26.cache.model.BitCoinPriceCached

@Dao
abstract class BitCoinChartCachedDao {
    @Query(Constant.QUERY_BIT_COIN_CHART)
    abstract fun getBitCoinChart(): BitCoinChartCached?

    @Query(Constant.QUERY_BIT_COIN_PRICE)
    abstract fun getBitCoinPrices(bitCoinChartId: Long): List<BitCoinPriceCached>

    @Query(Constant.DELETE_ALL_BIT_COIN_CHART)
    abstract fun clearBitCoinChart()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBitCoinChart(bitCoinChartCached: BitCoinChartCached) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBitCoinPrices(bitCoinPrices: List<BitCoinPriceCached>)
}