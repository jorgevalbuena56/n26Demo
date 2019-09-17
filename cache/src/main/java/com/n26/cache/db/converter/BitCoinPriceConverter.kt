package com.n26.cache.db.converter

import android.arch.persistence.room.TypeConverter
import java.math.BigDecimal

class BitCoinPriceConverter {
    @TypeConverter
    fun toBigDecimal(value: Double): BigDecimal {
        return BigDecimal.valueOf(value)
    }

    @TypeConverter
    fun toDouble(value: BigDecimal): Double {
        return value.toDouble()
    }
}