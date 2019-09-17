package com.n26.cache.mapper

import com.n26.cache.factory.BitCoinChartFactoryMock
import com.n26.cache.model.BitCoinChartCached
import com.n26.data.render.BitCoinChart
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File
import java.io.FileReader
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BitCoinChartMapperTest {
    private val bitCoinChartEntityManager = BitCoinChartEntityMapper()

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @Test
    fun mapToCachedMapsData() {
        val resourceFile = BitCoinChart::class.java.classLoader.getResource("bitcoinchart.json")
        val f = File(resourceFile!!.toURI())
        val reader = JsonReader(FileReader(f))
        val bitCoinChartEntity = GsonBuilder().create().fromJson(reader, BitCoinChart::class.java) as BitCoinChart
        val bitCoinChartCached = bitCoinChartEntityManager.mapToCached(bitCoinChartEntity)

        assertBitCoinChartDataEquality(bitCoinChartEntity, bitCoinChartCached)
    }

    @Test
    fun mapFromCachedMapsData() {
        val bitCoinChartCached = BitCoinChartFactoryMock.makeBitCoinChart()
        val bitCoinChartEntity = bitCoinChartEntityManager.mapFromCached(bitCoinChartCached)

        assertBitCoinChartDataEquality(bitCoinChartEntity, bitCoinChartCached)
    }

    private fun assertBitCoinChartDataEquality(bitCoinChart: BitCoinChart,
                                               bitcoinChartCached: BitCoinChartCached
    ) {
        assertEquals(bitCoinChart.status, bitcoinChartCached.status)
        assertEquals(bitCoinChart.unit, bitcoinChartCached.unit)
        assertEquals(bitCoinChart.name, bitcoinChartCached.name)
        assertEquals(bitCoinChart.description, bitcoinChartCached.description)
        assertEquals(bitCoinChart.period, bitcoinChartCached.period)
    }
}