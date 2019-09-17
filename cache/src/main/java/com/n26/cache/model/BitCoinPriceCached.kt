package com.n26.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.n26.cache.model.BitCoinChartCached
import java.math.BigDecimal

/**
 * Model to store independently the bit coin prices
 */
@Entity(tableName = "BitCoinPrice", indices = [Index("bitCoinChartId")],
        foreignKeys = [ForeignKey(entity = BitCoinChartCached::class,
                                  parentColumns = ["id"],
                                  childColumns = ["bitCoinChartId"],
                                  onDelete = ForeignKey.CASCADE)])
data class BitCoinPriceCached(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,

    val bitCoinChartId: Long,
    val x: BigDecimal,
    val y: BigDecimal )