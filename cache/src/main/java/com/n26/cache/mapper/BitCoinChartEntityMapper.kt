package com.n26.cache.mapper

import com.n26.cache.model.BitCoinChartCached
import com.n26.data.render.BitCoinChart
import com.n26.data.render.BitCoinPrice

/**
 * Map a [BitCoinChartCached] instance to and from a [BitCoinChart] instance when data is moving between
 * this later and the Data layer
 */
open class BitCoinChartEntityMapper : EntityMapper<BitCoinChartCached, BitCoinChart> {
    /**
     * Map a [BitCoinChartCached] instance to a [BitCoinChart] instance
     */
    override fun mapFromCached(type: BitCoinChartCached): BitCoinChart {
        return BitCoinChart(type.status, type.name, type.unit, type.period, type.description,
            type.values.map { BitCoinPrice(it.x, it.y) })
    }

    /**
     * Map a [BitCoinChart] instance to a [BitCoinChartCached] instance
     */
    override fun mapToCached(type: BitCoinChart): BitCoinChartCached {
        return BitCoinChartCached(name = type.name, status = type.status, unit = type.unit,
            period = type.period, description = type.description)
    }
}