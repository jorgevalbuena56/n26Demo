package com.n26.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.n26.cache.db.converter.BitCoinPriceConverter
import com.n26.cache.db.dao.BitCoinChartCachedDao
import com.n26.cache.model.BitCoinChartCached
import com.n26.cache.model.BitCoinPriceCached

@Database(entities = [BitCoinChartCached::class, BitCoinPriceCached::class], version = Constant.version)
@TypeConverters(value = [BitCoinPriceConverter::class])
abstract class BitCoinChartDatabase: RoomDatabase() {
    abstract fun bitCoinchartCachedDao() : BitCoinChartCachedDao
}