package com.n26.remote.mapper

import com.n26.remote.factory.BitCoinChartFactoryMock
import com.n26.remote.mapper.BitCoinChartEntityMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BitCoinMapperTest {
    private val bitCoinChartEntityMapper = BitCoinChartEntityMapper()

    @Test
    fun mapFromRemoteMapsData() {
        val bitCoinChartModel = BitCoinChartFactoryMock.makeBitCoinChart()
        val bitCoinChart = bitCoinChartEntityMapper.mapFromRemote(bitCoinChartModel)

        assertEquals(bitCoinChartModel.name, bitCoinChart.name)
        assertEquals(bitCoinChartModel.description, bitCoinChart.description)
        assertEquals(bitCoinChartModel.period, bitCoinChart.period)
        assertEquals(bitCoinChartModel.status, bitCoinChart.status)
        assertEquals(bitCoinChartModel.unit, bitCoinChart.unit)
        assertEquals(bitCoinChartModel.values.size, bitCoinChart.values.size)
    }
}