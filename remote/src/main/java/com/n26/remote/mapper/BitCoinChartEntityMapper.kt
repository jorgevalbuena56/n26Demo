package com.n26.remote.mapper

import com.n26.data.render.BitCoinChart
import com.n26.data.render.BitCoinPrice
import com.n26.remote.model.BitCoinChartModel

/**
 * Map a [BitCoinChartModel] to and from a [BitCoinChart] instance when data is moving between
 * this later and the Data layer
 */
open class BitCoinChartEntityMapper : EntityMapper<BitCoinChartModel, BitCoinChart> {
    /**
     * Map an instance of a [BitCoinChartModel] to a [BitCoinChart] data model
     */
    override fun mapFromRemote(type: BitCoinChartModel): BitCoinChart {
        val pricesModel = type.values.map { BitCoinPrice(it.x, it.y) }
        return BitCoinChart(type.status, type.name, type.unit, type.period, type.description, pricesModel)
    }
}