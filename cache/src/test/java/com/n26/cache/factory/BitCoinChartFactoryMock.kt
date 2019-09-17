package com.n26.cache.factory

import com.n26.cache.model.BitCoinChartCached
import com.n26.cache.utils.getClassFromResource

class BitCoinChartFactoryMock {
    companion object {
        fun makeBitCoinChart(): BitCoinChartCached {
            return getClassFromResource("bitcoinchart.json")
        }
    }
}