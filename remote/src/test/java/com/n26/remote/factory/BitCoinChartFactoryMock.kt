package com.n26.remote.factory

import com.n26.remote.model.BitCoinChartModel
import com.n26.remote.utils.getClassFromResource

class BitCoinChartFactoryMock {
    companion object {
        fun makeBitCoinChart(): BitCoinChartModel {
            return getClassFromResource("bitcoinchart.json")
        }
    }
}