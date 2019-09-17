package com.n26.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Model used solely for the caching of a bitCoinChart
 */
@Entity(tableName = "BitCoinChart")
data class BitCoinChartCached(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    var name: String = "",
    var status: String = "",
    var unit:String = "",
    var period: String = "",
    var description: String = "",
    @Ignore
    var values: List<BitCoinPriceCached> = ArrayList()
)