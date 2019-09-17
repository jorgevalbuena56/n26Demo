package com.n26.data.factory

import com.n26.data.render.BitCoinChart
import com.n26.data.utils.getClassFromResource

class BitCoinChartFactoryMock {
    companion object {
        fun makeBitCoinChart(): BitCoinChart {
            return getClassFromResource("bitcoinchart.json")
        }
    }
}